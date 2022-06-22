package com.tdsast.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JoinReturn(val success: Boolean, val message: String) {
    companion object {
        fun success(message: String) = JoinReturn(true, message)
        fun error(message: String) = JoinReturn(false, message)
    }
}

@Serializable
data class JoinRequest(
    val name: String,
    @SerialName("student_id")
    val studentId: String,
    val club: Long,
    val phone: String,
    val qq: String,
    val counselor: String,
    @SerialName("first_choice")
    val firstChoice: Long,
    @SerialName("second_choice")
    val secondChoice: Long,
    val reason: String
)