package com.tdsast.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Club(
    /**
     * ID
     */
    val id: Long,
    /**
     * 社团名称
     */
    val name: String
)

object Clubs : Table() {
    val id = long("id").autoIncrement()
    val name = varchar("name", 255)

    override val primaryKey = PrimaryKey(id)
}
