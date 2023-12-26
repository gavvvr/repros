FROM bellsoft/liberica-openjdk-alpine-musl:21 as builder
WORKDIR /build_dir
COPY gradle/wrapper gradle/wrapper
COPY gradlew* .
RUN ./gradlew
COPY *.kts .
RUN ./gradlew :clean --info # for generating '.gradle/caches/8.5/generated-gradle-jars/gradle-api-8.5.jar' and resolving dependencies for Gradle plugins
RUN ./gradlew :assemble --info # without sources, for resolving dependencies
COPY src/main src/main
RUN ./gradlew :assemble # for compilation
RUN ./gradlew :install --info # for preparing 'distribution'

FROM bellsoft/liberica-openjdk-alpine-musl:21
COPY --from=builder /build_dir/build/install/events-exporter/ /app/
ENTRYPOINT ["/app/bin/exporter"]
