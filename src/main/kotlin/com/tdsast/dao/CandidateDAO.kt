package com.tdsast.dao

import com.tdsast.dao.DatabaseFactory.dbQuery
import com.tdsast.dao.exceptions.ElementNotFoundException
import com.tdsast.model.Candidate
import com.tdsast.model.Candidates
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

interface CandidateDAO {

    /**
     * 获取所有候选人
     */
    suspend fun allCandidates(): List<Candidate>

    /**
     * 根据 id 获取候选人
     */
    suspend fun candidateById(id: Long): Candidate?

    /**
     * 根据姓名获取候选人
     */
    suspend fun candidateByName(name: String): List<Candidate>

    /**
     * 根据学号获取候选人
     */
    suspend fun candidateByStudentId(studentId: String): List<Candidate>

    /**
     * 添加候选人
     */
    suspend fun addNewCandidate(candidate: Candidate): Candidate?

    /**
     * 根据 id 删除候选人
     */
    suspend fun deleteCandidateById(id: Long): Boolean
}

class CandidateDaoImpl : CandidateDAO {
    private fun resultRowToCandidate(row: ResultRow): Candidate {
        val id = row[Candidates.id]
        val name = row[Candidates.name]
        val studentId = row[Candidates.studentId]
        val club = runBlocking {
            ClubDAOImpl().clubById(row[Candidates.club])
        } ?: throw ElementNotFoundException("Club")
        val phone = row[Candidates.phone]
        val qq = row[Candidates.qq]
        val counselor = row[Candidates.counselor]
        val firstChoice = runBlocking {
            DepartmentDAOImpl().departmentById(row[Candidates.firstChoice])
        } ?: throw ElementNotFoundException("Department")
        val secondChoice = runBlocking {
            DepartmentDAOImpl().departmentById(row[Candidates.secondChoice])
        } ?: throw ElementNotFoundException("Department")
        val reason = row[Candidates.reason]
        return Candidate(id, name, studentId, club, phone, qq, counselor, firstChoice, secondChoice, reason)
    }

    override suspend fun allCandidates() = dbQuery {
        Candidates
            .selectAll()
            .map(::resultRowToCandidate)
    }

    override suspend fun candidateById(id: Long) = dbQuery {
        Candidates
            .select { Candidates.id eq id }
            .map(::resultRowToCandidate)
            .singleOrNull()
    }

    override suspend fun candidateByName(name: String) = dbQuery {
        Candidates
            .select { Candidates.name eq name }
            .map(::resultRowToCandidate)
    }

    override suspend fun candidateByStudentId(studentId: String) = dbQuery {
        Candidates
            .select { Candidates.studentId eq studentId }
            .map(::resultRowToCandidate)
    }

    override suspend fun addNewCandidate(candidate: Candidate) = dbQuery {
        val insertStatement = Candidates.insert {
            it[name] = candidate.name
            it[studentId] = candidate.studentId
            it[club] = candidate.club.id
            it[phone] = candidate.phone
            it[qq] = candidate.qq
            it[counselor] = candidate.counselor
            it[firstChoice] = candidate.firstChoice.id
            it[secondChoice] = candidate.secondChoice.id
            it[reason] = candidate.reason
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToCandidate)
    }

    override suspend fun deleteCandidateById(id: Long) = dbQuery {
        Candidates
            .deleteWhere { Candidates.id eq id } > 0
    }
}
