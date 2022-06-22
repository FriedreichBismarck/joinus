package com.tdsast.router

import com.tdsast.plugins.configureRouting
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.server.testing.testApplication
import kotlin.test.Test

class ClubRouterTest {
    @Test
    fun getClubsTest() = testApplication {
        application {
            configureRouting()
        }
        client.get("/club").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun getClubTest() = testApplication {
        application {
            configureRouting()
        }
        client.get("/club/{id}").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun addClubTest() = testApplication {
        application {
            configureRouting()
        }
        client.post("/club").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun deleteClubTest() = testApplication {
        application {
            configureRouting()
        }
        client.delete("/club/{id}").apply {
            TODO("Please write your test here")
        }
    }
}
