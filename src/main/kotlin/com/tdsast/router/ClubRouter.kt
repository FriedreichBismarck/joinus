package com.tdsast.router

import com.tdsast.dao.ClubDAOImpl
import com.tdsast.model.dto.ClubReturn
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Routing.clubRouter() {
    route("/club") {
        get {
            val clubDAO = ClubDAOImpl()
            val clubs = clubDAO.allClubs()
            return@get call.respond(HttpStatusCode.OK, ClubReturn.success("获取成功", null, clubs))
        }

        get("{id?}") {
            val id: Long
            try {
                id = call.parameters["id"]?.toLong() ?: -1
            } catch (_: Exception) {
                return@get call.respond(HttpStatusCode.BadRequest, ClubReturn.error("ID不合法"))
            }
            if (id == -1L) {
                return@get call.respond(HttpStatusCode.BadRequest, ClubReturn.error("ID不合法"))
            }
            val clubDAO = ClubDAOImpl()
            val club =
                clubDAO.clubById(id) ?: return@get call.respond(HttpStatusCode.NotFound, ClubReturn.error("ID不存在"))
            return@get call.respond(HttpStatusCode.OK, ClubReturn.success("获取成功", club, null))
        }

        post {
            TODO("创建社团")
        }

        delete("{id?}") {
            val id = call.parameters["id"]?.toLong() ?: -1
            if (id == -1L) {
                return@delete call.respond(HttpStatusCode.BadRequest, ClubReturn.error("ID不合法"))
            }
            val clubDAO = ClubDAOImpl()
            if (clubDAO.deleteClub(id)) {
                return@delete call.respond(HttpStatusCode.OK, ClubReturn.success("删除成功", null, null))
            } else {
                return@delete call.respond(HttpStatusCode.NotFound, ClubReturn.error("删除失败"))
            }
        }
    }
}
