rootProject.name = "gradle-overrides-jackson-with-lower-version"

include("subproject-with-buf")

pluginManagement {
    includeBuild("gradle/build-logic")
}
