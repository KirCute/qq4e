plugins {
    kotlin("jvm") version "1.6.10"
    java
}

group = "org.qq4e"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    api("net.mamoe", "mirai-core", "2.11.0")
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}