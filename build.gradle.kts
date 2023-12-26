plugins {
    application
    kotlin("jvm") version "1.9.22"
}

application {
    mainClass = "com.example.EventsExporterKt"
}

distributions {
    main {
        distributionBaseName = "events-exporter"
        tasks.startScripts {
            applicationName = "exporter"
        }
    }
}



repositories {
    mavenCentral()
}

dependencies {
    val occurrentVersion = "0.16.11"
    implementation("org.occurrent:eventstore-mongodb-native:$occurrentVersion")
    implementation("org.occurrent:subscription-mongodb-native-blocking-position-storage:$occurrentVersion")
    implementation("org.occurrent:catchup-subscription:$occurrentVersion")
    implementation("org.occurrent:competing-consumer-subscription:$occurrentVersion")

    implementation("org.occurrent:cloudevent-converter-jackson:$occurrentVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.3")

    implementation("ch.qos.logback:logback-classic:1.4.14")

    implementation("org.mongodb:mongodb-driver-kotlin-sync:4.11.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
