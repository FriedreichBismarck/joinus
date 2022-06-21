package com.tdsast.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Candidate(
    /**
     * ID
     */
    val id: Long,
    /**
     * 姓名
     */
    val name: String,
    /**
     * 学号
     */
    val studentId: String,
    /**
     * 社团
     */
    val club: Club,
    /**
     * 电话
     */
    val phone: String,
    /**
     * QQ号
     */
    val qq: String,
    /**
     * 辅导员姓名
     */
    val counselor: String,
    /**
     * 第一志愿
     */
    val firstChoice: Department,
    /**
     * 第二志愿
     */
    val secondChoice: Department,
    /**
     * 加入原因
     */
    val reason: String
)

object Candidates : Table() {
    val id = long("id").autoIncrement()
    val name = varchar("name", 50)
    val studentId = varchar("student_id", 12)
    val club = reference("club_id", Clubs.id)
    val phone = varchar("phone", 11)
    val qq = varchar("qq", 15)
    val counselor = varchar("counselor", 50)
    val firstChoice = reference("first_choice", Departments.id)
    val secondChoice = reference("second_choice", Departments.id)
    val reason = varchar("reason", 300)

    override val primaryKey = PrimaryKey(id)
}
