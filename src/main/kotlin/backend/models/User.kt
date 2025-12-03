// Kotlin
package backend.models

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int = 0,
    val email: String = "",
    val nombre: String = "",
    @SerializedName("is_admin") val isAdmin: Boolean = false
)
