plugins {
    java
}

dependencies {
    implementation(platform(("com.google.protobuf:protobuf-bom:3.25.4")))
    implementation("com.google.protobuf:protobuf-java")
}
