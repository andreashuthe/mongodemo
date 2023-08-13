description = "demo-dtos"
plugins {
}
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
    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")
}

