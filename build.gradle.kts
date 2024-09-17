plugins {
    id("java")
    id("jib-configuration-convention")
}

allprojects {
    tasks.register("printClasspath") {
        doLast {
            buildscript.configurations
                .named("classpath").get()
                .asFileTree
                .filter { it.name.contains("jackson")}
                .forEach { println(it) }
        }
    }
}
