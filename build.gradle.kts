plugins {
    kotlin("jvm") version "1.6.21"
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
