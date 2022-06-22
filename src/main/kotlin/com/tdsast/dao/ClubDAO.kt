package com.tdsast.dao

import com.tdsast.dao.DatabaseFactory.dbQuery
import com.tdsast.model.Club
import com.tdsast.model.Clubs
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

interface ClubDAO {

    /**
     * 获取所有社团
     */
    suspend fun allClubs(): List<Club>

    /**
     * 根据 ID 获取社团
     */
    suspend fun clubById(id: Long): Club?

    /**
     * 根据名称获取社团
     */
    suspend fun clubByName(name: String): List<Club>

    /**
     * 添加社团
     */
    suspend fun addClub(club: Club): Club?

    /**
     * 根据 ID 删除社团
     */
    suspend fun deleteClub(id: Long): Boolean
}

class ClubDAOImpl : ClubDAO {

    private fun resultRowToClub(row: ResultRow) = Club(
        id = row[Clubs.id],
        name = row[Clubs.name]
    )

    override suspend fun allClubs() = dbQuery {
        Clubs
            .selectAll()
            .map(::resultRowToClub)
    }

    override suspend fun clubById(id: Long) = dbQuery {
        Clubs
            .select { Clubs.id eq id }
            .map(::resultRowToClub)
            .singleOrNull()
    }

    override suspend fun clubByName(name: String) = dbQuery {
        Clubs
            .select { Clubs.name eq name }
            .map(::resultRowToClub)
    }

    override suspend fun addClub(club: Club) = dbQuery {
        val insertStatement = Clubs.insert {
            it[name] = club.name
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToClub)
    }

    override suspend fun deleteClub(id: Long) = dbQuery {
        Clubs
            .deleteWhere { Clubs.id eq id } > 0
    }
}
