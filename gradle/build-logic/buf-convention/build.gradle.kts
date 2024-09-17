plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("com.google.protobuf:protobuf-gradle-plugin:0.9.4")
//    implementation("build.buf:buf-gradle-plugin:0.9.1") // <-- use this version for :subproject-with-buf:publishBufImagePublicationPublicationToLocalArtifactsRepository task
    implementation("build.buf:buf-gradle-plugin:0.10.0")
}
