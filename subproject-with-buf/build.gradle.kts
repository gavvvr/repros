plugins {
    id("buf-check-convention")
}

sourceSets {
    main {
        proto {
            srcDirs("src/main/proto")
        }
    }
}
