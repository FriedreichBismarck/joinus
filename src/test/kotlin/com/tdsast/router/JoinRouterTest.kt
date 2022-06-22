package com.tdsast.router

import com.tdsast.plugins.configureRouting
import io.ktor.client.request.post
import io.ktor.server.testing.testApplication
import kotlin.test.Test

class JoinRouterTest {

    @Test
    fun postJoinTest() = testApplication {
        application {
            configureRouting()
        }
        client.post("/join").apply {
            TODO("Please write your test here")
        }
    }

}
