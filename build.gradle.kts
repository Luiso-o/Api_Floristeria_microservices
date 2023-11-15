plugins {
	java
	id("org.springframework.boot") version "3.1.4"
	id("io.spring.dependency-management") version "1.1.3"
}

group = "ProyectoFloristeria"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web:3.1.0")
	developmentOnly("org.springframework.boot:spring-boot-devtools:3.1.0")
	implementation("org.springframework.boot:spring-boot-starter-webflux:3.0.4")
	implementation("io.projectreactor:reactor-core:3.5.11")
	implementation("org.yaml:snakeyaml:2.0")

	// Spring Data MongoDB
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb:3.1.0")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive:3.0.4")

	// Lombok
	compileOnly("org.projectlombok:lombok:1.18.28")
	annotationProcessor("org.projectlombok:lombok:1.18.28")

	// Springdoc for Swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.4")
	implementation("io.swagger:swagger-annotations:1.6.11")

	// SLF4J
	implementation("org.slf4j:slf4j-api:2.0.9")

	// Test
	testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
	testImplementation("io.projectreactor:reactor-test:3.5.4")
}

tasks.withType<Test> {
	useJUnitPlatform()
}


