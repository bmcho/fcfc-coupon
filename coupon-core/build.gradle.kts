val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks
bootJar.enabled = false

repositories {
    mavenCentral()
}

dependencies {

}

tasks.withType<Test> {
    useJUnitPlatform()
}
