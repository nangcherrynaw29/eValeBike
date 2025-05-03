plugins {
    id("java")
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    id ("com.github.spotbugs") version "6.0.26"
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
    runtimeOnly("org.postgresql:postgresql:42.7.5")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation ("com.h2database:h2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
    testImplementation ("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation ("org.testcontainers:testcontainers:1.20.4")
    testImplementation ("org.testcontainers:postgresql:1.20.4")
    testImplementation ("org.testcontainers:junit-jupiter:1.20.4")
    testImplementation ("net.bytebuddy:byte-buddy:1.15.11")
    testImplementation ("net.bytebuddy:byte-buddy-agent:1.15.11")
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

}

//spotbugs {
//    effort = 'max'  // Maximum analysis effort for thorough scanning
//    reportLevel = 'high'  // Report high-confidence security issues
//}
//
//tasks.named('spotbugsMain') {
//    reports {
//        xml {
//            enabled = true
//            destination = file("$buildDir/reports/spotbugs/main.xml")
//        }
//        html {
//            enabled = true
//            destination = file("$buildDir/reports/spotbugs/main.html")
//        }
//    }
//}

tasks.withType<Test> {
    useJUnitPlatform()
}

