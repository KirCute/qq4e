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

    api("net.mamoe", "mirai-core", "2.11.1")
    api(platform("net.mamoe:mirai-bom:2.11.1"))
    api("net.mamoe:mirai-core-api")
    runtimeOnly("net.mamoe:mirai-core")

    api("org.slf4j", "slf4j-api", "1.7.25")
    runtimeOnly("org.slf4j", "slf4j-simple", "1.7.25")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}