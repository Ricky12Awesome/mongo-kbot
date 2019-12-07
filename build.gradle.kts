val ktorVersion = "1.2.4"

plugins {
  kotlin("jvm") version "1.3.61"
  kotlin("plugin.serialization") version "1.3.61"
}

repositories {
  maven("https://kotlin.bintray.com/kotlinx")
  mavenCentral()
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation("org.litote.kmongo:kmongo:3.11.1")
  implementation("org.mongodb:mongodb-driver-async:3.11.2")
  implementation("org.javacord:javacord:3.0.4")
  implementation("com.google.guava:guava:28.1-jre")
  implementation("ch.qos.logback:logback-classic:1.2.1")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.13.0")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.3.2")
  implementation("io.ktor:ktor-serialization:$ktorVersion")
  implementation("io.ktor:ktor-client-core:$ktorVersion")
  implementation("io.ktor:ktor-client-core-jvm:$ktorVersion")
  implementation("io.ktor:ktor-client-cio:$ktorVersion")
  implementation("io.ktor:ktor-client-json-jvm:$ktorVersion")
  implementation("io.ktor:ktor-client-serialization-jvm:$ktorVersion")
  implementation("io.ktor:ktor-client-logging-jvm:$ktorVersion")
}

tasks {
  compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
  }

  compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
  }
}

kotlin.sourceSets["main"].kotlin.srcDirs("main/src/")
sourceSets["main"].resources.srcDirs("main/resources/")

kotlin.sourceSets["test"].kotlin.srcDirs("test/src/")
sourceSets["test"].resources.srcDirs("test/resources/")
