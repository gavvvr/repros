package com.example

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callid.CallId
import io.ktor.server.plugins.callid.callIdMdc
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.launch
import org.slf4j.MDC

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
            launch {
                println("Is null with Ktor v3: " + MDC.get(CLIENT_ID_MDC_KEY))
            }
            call.respondText("Retrieved as expected: " + MDC.get(CLIENT_ID_MDC_KEY))
        }
    }
}
