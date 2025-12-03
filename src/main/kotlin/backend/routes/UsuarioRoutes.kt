
package backend.routes

import backend.models.User
import backend.repo.EmailAlreadyExistsException
import backend.repo.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private val userRepo = UserRepository()

fun Route.usuarioRoutes() {
    route("/usuarios") {
        // GET /usuarios/{email}
        get("{email}") {
            val email = call.parameters["email"] ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "email requerido"))
            val user = userRepo.getByEmail(email)
            if (user == null) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "usuario no encontrado"))
            } else {
                call.respond(user)
            }
        }

        // POST /usuarios
        post {
            val userReq = runCatching { call.receive<User>() }.getOrElse {
                return@post call.respond(HttpStatusCode.BadRequest, mapOf("error" to "body inválido"))
            }
            try {
                val created = userRepo.create(userReq)
                call.respond(HttpStatusCode.Created, created)
            } catch (e: EmailAlreadyExistsException) {
                call.respond(HttpStatusCode.Conflict, mapOf("error" to "email ya existe"))
            }
        }
    }
}
