// Kotlin
package backend.models

data class Pokemon(
    val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val tipos: List<String>,
    val imagenUrl: String,
    val habilidades: List<String>? = null,
    val wikiUrl: String? = null
)
