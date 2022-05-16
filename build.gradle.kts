plugins {
    val kotlinVersion = "1.6.20"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.10.2"
}

group = "moe.ruabbit"
version = "0.1.0"

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}

dependencies {

    implementation("org.jetbrains.exposed", "exposed-core", "0.38.1")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.38.1")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.38.1")

    implementation("mysql:mysql-connector-java:8.0.29")
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")
    implementation("com.zaxxer:HikariCP:5.0.1")

    // https://mvnrepository.com/artifact/cn.hutool/hutool-all
    // implementation("cn.hutool:hutool-all:5.7.22")
    // implementation("org.ktorm:ktorm-core:3.4.1")
    // https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core
    // implementation("org.apache.logging.log4j:log4j-core:2.17.2")
    // https://mvnrepository.com/artifact/log4j/log4j
    // implementation("log4j:log4j:1.2.17")

}