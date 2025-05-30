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

    implementation("com.github.ben-manes.caffeine:caffeine:3.2.0")

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.core:jackson-databind")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
