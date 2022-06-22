package com.tdsast.router

import com.tdsast.dao.CandidateDaoImpl
import com.tdsast.dao.ClubDAOImpl
import com.tdsast.dao.DepartmentDAOImpl
import com.tdsast.dao.exceptions.ElementNotFoundException
import com.tdsast.model.Candidate
import com.tdsast.model.dto.JoinRequest
import com.tdsast.model.dto.JoinReturn
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory

private fun requestToCandidate(request: JoinRequest): Candidate {
    val departmentDAO = DepartmentDAOImpl()
    return Candidate(
        id = 0,
        name = request.name,
        studentId = request.studentId,
        club = runBlocking { ClubDAOImpl().clubById(request.club) } ?: throw ElementNotFoundException("Club"),
        phone = request.phone,
        qq = request.qq,
        counselor = request.counselor,
        firstChoice = runBlocking { departmentDAO.departmentById(request.firstChoice) }
            ?: throw ElementNotFoundException("Department"),
        secondChoice = runBlocking { departmentDAO.departmentById(request.secondChoice) }
            ?: throw ElementNotFoundException("Department"),
        reason = request.reason
    )
}

fun Routing.join() {

    val logger = LoggerFactory.getLogger(javaClass)

    route("/join") {
        post {
            val result: Candidate?
            val candidate: Candidate?
            try {
                val request = call.receive<JoinRequest>()
                candidate = requestToCandidate(request)
                logger.info("Received candidate: $request")
                val candidateDao = CandidateDaoImpl()
                result = candidateDao.addNewCandidate(candidate)
            } catch (e: ElementNotFoundException) {
                logger.error("Error adding candidate: ${e.message}")
                call.respond(JoinReturn.error("数据不存在"))
                return@post
            } catch (e: Exception) {
                logger.error(e.message)
                call.respond(JoinReturn.error("数据错误"))
                return@post
            }
            if (result != null) {
                logger.info("Successfully added candidate: $candidate")
                call.respond(JoinReturn.success("申请成功"))
            } else {
                logger.info("Failed to add candidate: $candidate")
                call.respond(JoinReturn.error("申请失败"))
            }
            return@post
        }
    }

}
