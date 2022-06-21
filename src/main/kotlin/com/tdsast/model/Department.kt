package com.tdsast.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Department(
    /**
     * ID
     */
    val id: Long,
    /**
     * 社团
     */
    val club: Club,
    /**
     * 部门名
     */
    val name: String
)

object Departments : Table() {
    val id = long("id").autoIncrement()
    val club = reference("club", Clubs.id)
    val name = varchar("name", 50)

    override val primaryKey = PrimaryKey(id)
}