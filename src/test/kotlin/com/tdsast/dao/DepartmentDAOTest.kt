package com.tdsast.dao

import com.tdsast.model.Club
import com.tdsast.model.Clubs
import com.tdsast.model.Departments
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.BeforeClass

class DepartmentDAOTest {

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
            Departments.deleteAll()
            Clubs.deleteAll()
        }
    }

    @Test
    fun `test allDepartments`() {
        val c = transaction {
            Clubs.insert { it[name] = "test" }.resultedValues?.singleOrNull()?.let {
                Club(id = it[Clubs.id], name = it[Clubs.name])
            }
        }
        val departmentDAO = DepartmentDAOImpl()
        val list = listOf("dep 1", "dep 2", "dep 3")
        list.forEach { e ->
            transaction {
                Departments.insert {
                    it[name] = e
                    it[club] = c!!.id
                }
            }
        }
        val allDepartments = runBlocking { departmentDAO.allDepartments() }
        assertEquals(list.size, allDepartments.size)
        allDepartments.forEach {
            assertTrue(list.contains(it.name))
        }
    }

    @Test
    fun `test departmentById`() {
        TODO("not implemented")
    }

    @Test
    fun `test departmentByName`() {
        TODO("not implemented")
    }

    @Test
    fun `test addDepartment`() {
        TODO("not implemented")
    }

    @Test
    fun `test deleteDepartment`() {
        TODO("not implemented")
    }

}
