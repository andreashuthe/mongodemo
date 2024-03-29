description = "demo-services"

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
    implementation( "org.springframework:spring-context")
    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")
    implementation("org.springframework.data:spring-data-mongodb")
    api(project(":demo-dtos"))
    api(project(":demo-daos"))
}

