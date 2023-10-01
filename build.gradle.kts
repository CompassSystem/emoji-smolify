plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "compass_system"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.sksamuel.scrimage:scrimage-core:4.1.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}