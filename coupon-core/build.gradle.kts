val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks
bootJar.enabled = false

val jar: Jar by tasks
jar.enabled = true

repositories {
    mavenCentral()
}

plugins {
    `java-library`
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.redisson:redisson-spring-boot-starter:3.47.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
