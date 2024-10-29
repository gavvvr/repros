package com.example

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    routing {
        post("/articles") {
            @Serializable
            data class Article(val title: String, val isPublished: Boolean = false)
            try {
                call.receive<Article>()
            } catch (e: Throwable) {
                e.printStackTrace()
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = e.exceptionChain
                )
            }
            call.respond("")
        }
    }
}

val Throwable.exceptionChain: String
    get() = buildString {
        append(this@exceptionChain::class.simpleName)
        var e: Throwable? = this@exceptionChain.cause
        while (e != null) {
            append(" -> ${e::class.simpleName}")
            e = e.cause
        }
    }
