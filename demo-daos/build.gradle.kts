description = "demo-daos"

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }

    all {
        exclude(group = "org.bouncycastle")
        exclude(group = "bouncycastle")
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
}

sourceSets {
    main {
        java {
            srcDir("src/main/java")
            
        }
    }
}

dependencies {
    implementation("org.springframework.data:spring-data-mongodb")
    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")

    api(project(":demo-beans"))
}

