package backend.repo

import backend.models.User

class UserRepository {
    private val users = mutableListOf<User>()
    private var nextId = 1

    fun getAll(): List<User> = users.toList()

    fun getByEmail(email: String): User? = users.find { it.email == email }

    fun create(user: User): User {
        if (users.any { it.email == user.email }) throw EmailAlreadyExistsException()
        val newUser = user.copy(id = nextId++)
        users.add(newUser)
        return newUser
    }

    fun deleteByEmail(email: String): Boolean = users.removeIf { it.email == email }

    /**
     * Actualiza solo el campo isAdmin del usuario identificado por email.
     * Devuelve el usuario actualizado o null si no existe.
     */
    fun updateAdminByEmail(email: String, isAdmin: Boolean): User? {
        val idx = users.indexOfFirst { it.email == email }
        if (idx == -1) return null
        val updated = users[idx].copy(isAdmin = isAdmin)
        users[idx] = updated
        return updated
    }
}
