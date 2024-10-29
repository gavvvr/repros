package com.example

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun lightweightKtorTest() = testApplication {
        application {
            module()
        }
        val response = client.post("/articles") {
            contentType(ContentType.Application.Json)
            setBody("""{"title": "Hello", "isPublished": "invalid-value"}""")
        }
        assertEquals("BadRequestException -> JsonConvertException -> JsonDecodingException", response.bodyAsText())
    }
}
