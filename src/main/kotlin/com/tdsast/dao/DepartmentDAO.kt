package com.tdsast.dao

import com.tdsast.dao.DatabaseFactory.dbQuery
import com.tdsast.model.Department
import com.tdsast.model.Departments
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

interface DepartmentDAO {

    /**
     * 获取所有部门
     */
    suspend fun allDepartments(): List<Department>

    /**
     * 根据 id 获取部门
     */
    suspend fun departmentById(id: Long): Department?

    /**
     * 根据名称获取部门
     */
    suspend fun departmentByName(name: String): Department?

    /**
     * 添加部门
     */
    suspend fun addDepartment(department: Department): Department?

    /**
     * 删除部门
     */
    suspend fun deleteDepartment(id: Long): Boolean
}

class DepartmentDAOImpl : DepartmentDAO {

    private fun resultRowToDepartment(row: ResultRow) = Department(
        id = row[Departments.id],
        name = row[Departments.name],
        club = runBlocking {
            ClubDAOImpl().clubById(row[Departments.club])!!
        }
    )

    override suspend fun allDepartments() = dbQuery {
        Departments
            .selectAll()
            .map(::resultRowToDepartment)
    }

    override suspend fun departmentById(id: Long) = dbQuery {
        Departments
            .select { Departments.id eq id }
            .map(::resultRowToDepartment)
            .singleOrNull()
    }

    override suspend fun departmentByName(name: String) = dbQuery {
        Departments
            .select { Departments.name eq name }
            .map(::resultRowToDepartment)
            .singleOrNull()
    }

    override suspend fun addDepartment(department: Department) = dbQuery {
        val insertStatement = Departments.insert {
            it[name] = department.name
            it[club] = department.club.id
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToDepartment)
    }

    override suspend fun deleteDepartment(id: Long) = dbQuery {
        Departments
            .deleteWhere { Departments.id eq id } > 0
    }
}
