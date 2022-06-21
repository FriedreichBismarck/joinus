package com.tdsast

import com.tdsast.dao.DatabaseFactory
import com.tdsast.plugins.configureRouting
import com.tdsast.plugins.configureSecurity
import com.tdsast.plugins.configureSerialization
import io.ktor.server.application.Application

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    DatabaseFactory.init()
    configureRouting()
    configureSecurity()
    configureSerialization()
}
