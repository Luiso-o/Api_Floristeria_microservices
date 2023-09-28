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
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.1.3")
	implementation("org.springframework.boot:spring-boot-starter-web:3.1.3")
	developmentOnly("org.springframework.boot:spring-boot-devtools:3.1.3")
	testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.3")

	//Lombok
	compileOnly("org.projectlombok:lombok:1.18.28")
	annotationProcessor("org.projectlombok:lombok:1.18.28")

	//DataBase
	runtimeOnly("com.mysql:mysql-connector-j:8.0.33")

	//swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
	implementation("io.swagger:swagger-annotations:1.6.11")


}

tasks.withType<Test> {
	useJUnitPlatform()
}
