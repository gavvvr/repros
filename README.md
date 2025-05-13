# [KTOR-8501](https://youtrack.jetbrains.com/issue/KTOR-8501)

Consider this simple app:

```kotlin
private const val CLIENT_ID_MDC_KEY = "clientId"

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(CallId) {
        retrieveFromHeader("User-Agent")
        verify { callId: String ->
            callId.isNotEmpty()
        }
    }
    install(CallLogging) {
        callIdMdc(CLIENT_ID_MDC_KEY)
    }
    routing {
        get("/") {
            // Launches from the Application receiver as CoroutineScope
            launch { println("MDC available " + MDC.get(CLIENT_ID_MDC_KEY)) }
            
            // Launches from call scope, inheriting coroutineContext
            call.launch { println("MDC missing " + MDC.get(CLIENT_ID_MDC_KEY)) }

            call.respondText("Retrieved as expected: " + MDC.get(CLIENT_ID_MDC_KEY))
        }
    }
}
```

This is quite confusing for users, especially since the routing handler context in Ktor 2 implemented `CoroutineScope`, so migrating will cause mysterious problems like this.
