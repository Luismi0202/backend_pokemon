package backend.repo

import backend.models.User
import java.util.concurrent.atomic.AtomicInteger

class UserRepository {
    private val seq = AtomicInteger(1)
    private val usersByEmail = mutableMapOf<String, User>()

    fun create(user: User): User {
        if (usersByEmail.containsKey(user.email)) {
            throw EmailAlreadyExistsException()
        }
        val created = user.copy(id = seq.getAndIncrement())
        usersByEmail[user.email] = created
        return created
    }

    fun getByEmail(email: String): User? = usersByEmail[email]

    fun clear() {
        usersByEmail.clear()
        seq.set(1)
    }
}

class EmailAlreadyExistsException : RuntimeException("email exists")
