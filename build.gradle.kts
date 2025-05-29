plugins {
    id("java")
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "integration4"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.testcontainers:mssqlserver")
    runtimeOnly("org.postgresql:postgresql:42.7.5")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    runtimeOnly("com.microsoft.sqlserver:mssql-jdbc")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.webjars:bootstrap:5.3.3")
    implementation("org.webjars.npm:bootstrap-icons:1.11.1")
    implementation("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    implementation("com.google.zxing:core:3.5.0")
    implementation("com.google.zxing:javase:3.5.0")
    implementation("org.webjars.npm:html5-qrcode:2.3.8")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    compileOnly("org.projectlombok:lombok:1.18.30")  // Use the latest version
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    // Microsoft SQL Server JDBC Driver
    implementation("com.microsoft.sqlserver:mssql-jdbc:12.6.1.jre11") // Change jre version if needed

    // Testcontainers Core & SQL Server
    testImplementation("org.testcontainers:testcontainers:1.19.3")
    testImplementation("org.testcontainers:mssqlserver:1.19.3")
    runtimeOnly("com.h2database:h2")

    // Spring Boot Test Starter
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation ("org.testcontainers:junit-jupiter:1.19.7")
    testImplementation ("org.apache.commons:commons-lang3:3.12.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
