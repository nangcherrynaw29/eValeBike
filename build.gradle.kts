import com.github.spotbugs.snom.Effort
import com.github.spotbugs.snom.Confidence

plugins {
    id("java")
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.github.spotbugs") version "6.0.26"
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
    maven {
        url = uri("https://jitpack.io")
    }// Fallback for SpotBugs
}

dependencies {
    // Spring Boot Starters
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // Database
    runtimeOnly("org.postgresql:postgresql:42.7.5")
    testImplementation("com.h2database:h2")

    // UI & Frontend
    implementation("org.webjars:bootstrap:5.3.3")
    implementation("org.webjars.npm:bootstrap-icons:1.11.1")
    implementation("org.webjars.npm:html5-qrcode:2.3.8")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")

    // QR / JSON / XML
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    implementation("com.google.zxing:core:3.5.0")
    implementation("com.google.zxing:javase:3.5.0")

    // MapStruct
    implementation("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.testcontainers:junit-jupiter:1.20.4")
    testImplementation("org.testcontainers:postgresql:1.20.4")
    testImplementation("org.testcontainers:testcontainers:1.20.4")
    testImplementation("net.bytebuddy:byte-buddy:1.15.11")
    testImplementation("net.bytebuddy:byte-buddy-agent:1.15.11")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

spotbugs {
    effort.set(Effort.MAX)
    reportLevel.set(Confidence.HIGH)
    ignoreFailures.set(true)
}

//tasks.named<com.github.spotbugs.snom.SpotBugsTask>("spotbugsMain") {
//    enabled.set(false) // or true if you want it to run
//    reports {
//        xml.required.set(true)
//        xml.outputLocation.set(file("$buildDir/reports/spotbugs/main.xml"))
//        html.required.set(true)
//        html.outputLocation.set(file("$buildDir/reports/spotbugs/main.html"))
//    }
//}

tasks.named("build") {
    dependsOn("bootJar")
    // Optional: remove spotbugs if disabled above
    dependsOn.remove("spotbugsMain")
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs = listOf(
        "-javaagent:${configurations.testRuntimeClasspath.get().find { it.name.contains("byte-buddy-agent") }?.absolutePath}"
    )
    reports {
        junitXml.required.set(true)
        junitXml.outputLocation.set(file("$buildDir/test-results/test"))
        html.required.set(true)
    }
}

