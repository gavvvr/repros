plugins {
    id("java")
    id("com.google.protobuf")
    id("build.buf")
    id("maven-publish")
}

buf {
    publishSchema = true
    previousVersion = "0.0.1-SNAPSHOT"

//    toolVersion = "1.29.0"

    imageArtifact {
        groupId = "com.buf.image"
        artifactId = "app"
        version = "0.0.1-SNAPSHOT"
    }
}

val repoUri = uri("file://${rootProject.projectDir}/gradle/buf-repo")

repositories {
    mavenCentral()
    maven {
        url = repoUri
    }
}

publishing {
    repositories {
        maven {
            url = repoUri
            name = "LocalArtifacts"
        }
    }
}
