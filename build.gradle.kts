kotlin
plugins {
    kotlin("jvm") version "1.9.23"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}

val ktorVersion = "2.3.12"

dependencies {
    implementation(platform("io.ktor:ktor-bom:$ktorVersion"))
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-gson-jvm")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    testImplementation(kotlin("test"))
    testImplementation("io.ktor:ktor-server-tests-jvm")
}

application {
    mainClass.set("com.example.backend.ApplicationKt")
}

tasks {
    shadowJar {
        archiveFileName.set("pokemon-backend-all.jar")
        mergeServiceFiles()
        manifest {
            attributes("Main-Class" to "com.example.backend.ApplicationKt")
        }
    }
    jar {
        manifest {
            attributes("Main-Class" to "com.example.backend.ApplicationKt")
        }
    }
}

kotlin { jvmToolchain(17) }
