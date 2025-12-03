// Kotlin
package backend.repo

import backend.models.Pokemon
import java.util.concurrent.atomic.AtomicInteger

class PokemonRepository {
    private val seq = AtomicInteger(1)
    private val pokemons = mutableMapOf<Int, Pokemon>()

    init {
        // Seed básico
        val seed = listOf(
            Pokemon(
                id = 1,
                nombre = "Charmander",
                descripcion = "Pokémon tipo fuego.",
                tipos = listOf("Fuego"),
                imagenUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png",
                habilidades = listOf("Mar Llamas"),
                wikiUrl = "https://pokemon.fandom.com/es/wiki/Charmander"
            )
        )
        seed.forEach { p ->
            pokemons[p.id] = p
        }
        seq.set((pokemons.keys.maxOrNull() ?: 0) + 1)
    }

    fun list(): List<Pokemon> = pokemons.values.sortedBy { it.id }

    fun create(pokemon: Pokemon): Pokemon {
        val id = seq.getAndIncrement()
        val created = pokemon.copy(id = id)
        pokemons[id] = created
        return created
    }

    fun update(id: Int, pokemon: Pokemon): Pokemon? {
        val existing = pokemons[id] ?: return null
        val updated = pokemon.copy(id = id)
        pokemons[id] = updated
        return updated
    }

    fun delete(id: Int): Boolean = pokemons.remove(id) != null
}
