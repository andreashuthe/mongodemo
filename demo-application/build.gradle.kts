
description = "demo-application"

plugins {
    java
    war
    `java-library`
    id("com.github.node-gradle.node") version "3.5.1"
}


configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }

    all {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
}

extra["jakarta-servlet.version"] = "5.0.0"

sourceSets {
    main {
        java {
            srcDir("src/main/java")
        }
    }
}

node {
    version.set("18.14.2")
    npmVersion.set("9.5.0")
    download.set(true)
    nodeProjectDir.set(file("src/main/webapp/frontend/demo-app"))
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    implementation("org.apache.commons:commons-collections4:4.4")
    implementation("org.apache.commons:commons-lang3:3.12.0")

    implementation("org.springframework.boot:spring-boot-starter-jetty")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")

    api(project(":demo-services"))
}

tasks.war {
    exclude("/frontend/")
    archiveFileName.set("demo.war")
}

tasks.register<com.github.gradle.node.npm.task.NpxTask>("buildFrontend") {
    dependsOn("npmInstallApp")
    extension.nodeProjectDir.set(file("src/main/webapp/frontend/demo-app"))
    command.set("ng")
    args.set(listOf("build", "--base-href", "/demo/app/"))
    inputs.files("src/main/webapp/frontend/demo-app/package.json", "src/main/webapp/frontend/demo-app/package-lock.json", "src/main/webapp/frontend/v-db-app/angular.json", "src/main/webapp/frontend/v-db-app/tsconfig.json", "src/main/webapp/frontend/v-db-app/tsconfig.app.json")
    inputs.dir("src/main/webapp/frontend/demo-app/src")
    //inputs.dir(fileTree("src/main/webapp/frontend/v-db-app/node_modules").exclude(".cache"))
    outputs.dir("src/main/webapp/frontend/demo-app/dist")
    outputs.upToDateWhen { false }
}

tasks.register<com.github.gradle.node.npm.task.NpmInstallTask>("npmInstallApp") {
    dependsOn("deleteFrontend")
    nodeExtension.nodeProjectDir.set(file("src/main/webapp/frontend/demo-app"))
    nodeExtension.download.set(true)
    args.set(listOf("--force"))
}

tasks.register("buildFrontendDeployment") {
    val angular = tasks["buildFrontend"]
    dependsOn(angular)
}

tasks.register<Delete>("deleteFrontend") {
    delete(files("src/main/resources/app"))
}

tasks.register("buildDeployment") {
    val clean = tasks["clean"]
    val warFile = tasks["war"]
    val fe = tasks["buildFrontendDeployment"]

    dependsOn(clean, fe, warFile)

    fe.mustRunAfter(clean)
    warFile.mustRunAfter(fe)
}


tasks {
    val bootRun by getting(org.springframework.boot.gradle.tasks.run.BootRun::class) {
        //jvmArgs = listOf("-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5011", "-Dspring.profiles.active=embedded", "-Dcatalina.base=build/tmp/")
        jvmArgs = listOf("-Dspring.profiles.active=embedded")
    }
}
