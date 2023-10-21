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
	//Spring configuration
	implementation("org.springframework.boot:spring-boot-starter-web:3.1.3")
	developmentOnly("org.springframework.boot:spring-boot-devtools:3.1.3")
	testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.3")
	implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

	// Spring WebFlux y Spring Data R2DBC para programación reactiva
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc:3.0.4")
	implementation("org.springframework.boot:spring-boot-starter-webflux:3.0.4")
	implementation("dev.miku:r2dbc-mysql:0.8.2.RELEASE")
	implementation("org.yaml:snakeyaml:2.0")

	//DataBase
	runtimeOnly("com.mysql:mysql-connector-j:8.0.33")

	//Lombok
	compileOnly("org.projectlombok:lombok:1.18.28")
	annotationProcessor("org.projectlombok:lombok:1.18.28")

	//swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
	implementation("io.swagger:swagger-annotations:1.6.11")

	// https://mvnrepository.com/artifact/org.slf4j/slf4j-api
	implementation("org.slf4j:slf4j-api:2.0.9")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
