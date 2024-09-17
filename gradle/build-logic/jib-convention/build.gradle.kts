plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("com.google.cloud.tools:jib-gradle-plugin:3.4.3")
}
