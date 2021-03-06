package com.tdsast.dao

import com.tdsast.model.Clubs
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.BeforeClass
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ClubDAOTest {
    companion object {
        @JvmStatic
        @BeforeClass
        fun `connect db`() {
            DatabaseFactory.init()
        }
    }

    @BeforeTest
    fun `clear db`() {
        transaction {
            Clubs.deleteAll()
        }
    }

    @Test
    fun `test allClubs`() {
        val list = listOf("Club 1", "Club 2", "Club 3")
        list.forEach { e ->
            transaction {
                Clubs.insert { it[name] = e }
            }
        }
        val dao = ClubDAOImpl()
        val result = runBlocking { dao.allClubs() }
        assertEquals(list.size, result.size)
        result.forEach { e ->
            assertContains(list, e.name)
        }
    }

    @Test
    fun `test clubById`() {
        val list = listOf("Club 1", "Club 2", "Club 3")
        val ids = list.map { e ->
            val insertStatement = transaction { Clubs.insert { it[name] = e } }
            insertStatement.resultedValues?.singleOrNull()?.get(Clubs.id)!!
        }
        val dao = ClubDAOImpl()
        list.forEachIndexed { index, e ->
            val result = runBlocking { dao.clubById(ids[index]) }
            assertEquals(e, result?.name)
        }

        // test not found
        assertNull(runBlocking { dao.clubById(ids[ids.size - 1] + 10) })
    }

    @Test
    fun `test clubByName`() {
        val list = listOf("Club 1", "Club 1", "Club 2", "Club 3")
        list.map { e ->
            val insertStatement = transaction { Clubs.insert { it[name] = e } }
            insertStatement.resultedValues?.singleOrNull()?.get(Clubs.id)!!
        }
        val dao = ClubDAOImpl()
        list.forEach { e ->
            val result = runBlocking { dao.clubByName(e) }
            assertEquals(list.filter { it == e }.size, result.size)
        }

        // test not found
        assertEquals(0, runBlocking { dao.clubByName("Club 10") }.size)
    }

    @Test
    fun `test addClub`() {
        TODO("not implemented")
    }

    @Test
    fun `test deleteClub`() {
        TODO("not implemented")
    }
}
