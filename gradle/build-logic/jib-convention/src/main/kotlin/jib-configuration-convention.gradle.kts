plugins {
    id("com.google.cloud.tools.jib")
}

jib {
    from {
        image = "gcr.io/distroless/java21-debian12:debug"
    }
}
