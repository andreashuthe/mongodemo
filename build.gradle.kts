plugins {
	 java
	 id("org.springframework.boot") version "3.1.2"
  	 id("io.spring.dependency-management") version "1.1.2"
     id("org.asciidoctor.jvm.convert") version "3.3.2"
	 id("org.owasp.dependencycheck") version "8.2.1"
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
        mavenCentral()
    }

extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
  implementation("org.springframework.boot:spring-boot-starter-web")
  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}


allprojects {
    group = "de.mdb"
	version = "0.0.1-SNAPSHOT"
    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "org.owasp.dependencycheck")

    java.sourceCompatibility = JavaVersion.VERSION_17

    repositories {
        mavenCentral()
    }
}

subprojects {
    tasks.withType<JavaCompile>().configureEach {
        options.isFork = true
    }
}
