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
}
