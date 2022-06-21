package com.tdsast.dao

import com.tdsast.model.Candidates
import com.tdsast.model.Clubs
import com.tdsast.model.Departments
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val driverClassName =
            System.getenv("DRIVER_NAME") ?: "org.h2.Driver"
        val jdbcURL = System.getenv("JDBC_URL") ?: "jdbc:h2:file:~/joinus/data"
        val database = Database.connect(
            driver = driverClassName,
            url = jdbcURL,
            user = System.getenv("DB_USER") ?: "",
            password = System.getenv("DB_PASSWORD") ?: ""
        )
        transaction(database) {
            SchemaUtils.create(Candidates)
            SchemaUtils.create(Clubs)
            SchemaUtils.create(Departments)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
