# The `java.lang.NoSuchMethodError` error in `:subproject-with-buf:writeWorkspaceYaml` task

## Steps to reproduce

1. Change the version of Buf plugin to v0.9.1 
and execute `./gradlew :subproject-with-buf:publishBufImagePublicationPublicationToLocalArtifactsRepository`
to populate local Maven repo with the reference snapshot
(unfortunately Buf plugin v0.10.0 is [too eager](https://github.com/bufbuild/buf-gradle-plugin/issues/235)
at resolving dependencies, so this step is needed)
2. Switch back to Buf plugin v0.10.0 and execute `./gradlew :subproject-with-buf:writeWorkspaceYaml --stacktrace`,
you will see the `java.lang.NoSuchMethodError`