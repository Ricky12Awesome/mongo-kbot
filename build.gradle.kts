val ktorVersion = "1.2.6"

plugins {
  kotlin("jvm") version "1.3.61"
  kotlin("plugin.serialization") version "1.3.61"
  `maven-publish`
}

repositories {
  maven("https://kotlin.bintray.com/kotlinx")
  mavenCentral()
}

dependencies {
  compileOnly(kotlin("stdlib-jdk8"))
  testCompileOnly("ch.qos.logback:logback-classic:1.2.1")
  compile("org.litote.kmongo:kmongo:3.11.1")
  compile("org.mongodb:mongodb-driver-async:3.11.2")
  compile("org.javacord:javacord:3.0.4")
  compile("com.google.guava:guava:28.1-jre")
  compile("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.13.0")
  compile("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.3.2")
  compile("io.ktor:ktor-serialization:$ktorVersion")
  compile("io.ktor:ktor-client-core:$ktorVersion")
  compile("io.ktor:ktor-client-core-jvm:$ktorVersion")
  compile("io.ktor:ktor-client-cio:$ktorVersion")
  compile("io.ktor:ktor-client-json-jvm:$ktorVersion")
  compile("io.ktor:ktor-client-serialization-jvm:$ktorVersion") {
    isTransitive = false
  }
  compile("io.ktor:ktor-client-logging-jvm:$ktorVersion")
}

val sourcesJar by tasks.creating(Jar::class) {
  archiveClassifier.set("sources")

  from(sourceSets["main"].allSource)
  dependsOn(JavaPlugin.CLASSES_TASK_NAME)
}

val javadocJar by tasks.creating(Jar::class) {
  archiveClassifier.set("javadoc")

  from(tasks.javadoc)
  dependsOn(JavaPlugin.JAVADOC_TASK_NAME)
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      artifact(sourcesJar)
      artifact(javadocJar)

      from(components["java"])
    }
  }

  repositories {
    mavenLocal()
  }
}

tasks {
  jar {

    manifest {
      attributes(
        "Class-Path" to configurations.compile.get().joinToString(" ") { it.name }
      )
    }
  }

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
