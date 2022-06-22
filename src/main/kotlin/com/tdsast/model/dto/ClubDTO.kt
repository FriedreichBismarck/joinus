package com.tdsast.model.dto

import com.tdsast.model.Club
import kotlinx.serialization.Serializable

@Serializable
data class ClubReturn(val success: Boolean, val message: String, val data: Club?, val list: ClubWrapper?) {
    companion object {
        fun success(message: String, data: Club?, list: List<Club>?): ClubReturn {
            return if (data != null) {
                ClubReturn(true, message, data, null)
            } else if (list != null) {
                ClubReturn(true, message, null, ClubWrapper(list))
            } else {
                ClubReturn(true, message, null, null)
            }
        }

        fun error(message: String) = ClubReturn(false, message, null, null)
    }
}

@Serializable
data class ClubWrapper(val count: Int, val items: List<Club>) {
    constructor(items: List<Club>) : this(items.size, items)
}
