dependencies {
    implementation(project(":coupon-core"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}
