// Kotlin
package backend.routes

import backend.models.Pokemon
import backend.repo.PokemonRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private val pokemonRepo = PokemonRepository()

fun Route.pokemonRoutes() {
    route("/pokemons") {
        // GET /pokemons
        get {
            call.respond(pokemonRepo.list())
        }
        // POST /pokemons
        post {
            val req = runCatching { call.receive<Pokemon>() }.getOrElse {
                return@post call.respond(HttpStatusCode.BadRequest, mapOf("error" to "body inválido"))
            }
            val created = pokemonRepo.create(req)
            call.respond(HttpStatusCode.Created, created)
        }
        // PUT /pokemons/{id}
        put("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, mapOf("error" to "id inválido"))
            val req = runCatching { call.receive<Pokemon>() }.getOrElse {
                return@put call.respond(HttpStatusCode.BadRequest, mapOf("error" to "body inválido"))
            }
            val updated = pokemonRepo.update(id, req)
            if (updated == null) call.respond(HttpStatusCode.NotFound, mapOf("error" to "no encontrado"))
            else call.respond(updated)
        }
        // DELETE /pokemons/{id}
        delete("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, mapOf("error" to "id inválido"))
            val ok = pokemonRepo.delete(id)
            if (ok) call.respond(HttpStatusCode.NoContent)
            else call.respond(HttpStatusCode.NotFound, mapOf("error" to "no encontrado"))
        }
    }
}
