package com.tdsast.dao

import com.tdsast.model.Departments
import kotlin.test.BeforeTest
import kotlin.test.Test
import org.jetbrains.exposed.sql.deleteAll
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
        }
    }

    @Test
    fun `test allDepartments`() {
        TODO("not implemented")
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
