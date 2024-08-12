plugins {
//    java // either java plugin with explicit `implementation(platform(...))` resolves the issue
    id("test-report-aggregation")
//    id("jacoco-report-aggregation") // or uncommenting the `jacoco-report-aggregation` solves the problem too
}

repositories {
    mavenCentral()
}

dependencies {
//    implementation(platform(("com.google.protobuf:protobuf-bom:3.25.4")))
    subprojects.forEach {
        testReportAggregation(it)
    }
}

reporting {
    reports {
        @Suppress("UnstableApiUsage")
        val aggregateUnitTestsReport by creating(AggregateTestReport::class) {
            testType = TestSuiteType.UNIT_TEST
        }
    }
}
