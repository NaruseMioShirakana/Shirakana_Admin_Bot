plugins {
    val kotlinVersion = "1.5.30"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.9.2"
}

group = "org.fujiwara.shirakana.adminbot"
version = "Alpha"

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}

