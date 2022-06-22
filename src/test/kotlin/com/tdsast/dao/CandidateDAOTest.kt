package com.tdsast.dao

import com.tdsast.model.Candidates
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.BeforeClass
import kotlin.test.BeforeTest
import kotlin.test.Test

class CandidateDAOTest {

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
            Candidates.deleteAll()
        }
    }

    @Test
    fun `test allCandidates`() {
        TODO("not implemented")
    }

    @Test
    fun `test candidateById`() {
        TODO("not implemented")
    }

    @Test
    fun `test candidateByName`() {
        TODO("not implemented")
    }

    @Test
    fun `test candidateByStudentId`() {
        TODO("not implemented")
    }

    @Test
    fun `test addNewCandidate`() {
        TODO("not implemented")
    }

    @Test
    fun `test deleteCandidateById`() {
        TODO("not implemented")
    }
}
