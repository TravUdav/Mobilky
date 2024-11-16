package ru.mirea.andreevapk.data.repository

import ru.mirea.andreevapk.data.mock.MockFirebaseAuth
import ru.mirea.andreevapk.domain.model.User
import ru.mirea.andreevapk.domain.repository.UserRepository

class UserRepositoryImpl(private val mockAuth: MockFirebaseAuth) : UserRepository {

    override fun getUser(): User {
        val currentUser = mockAuth.getCurrentUser()
        return if (currentUser != null) {
            User(
                id = currentUser.uid.hashCode(),
                name = currentUser.displayName
            )
        } else {
            throw IllegalStateException("No user is logged in.")
        }
    }

    override fun logoutUser() {
        mockAuth.signOut()
    }

    override fun setName(name: String) {
        if (mockAuth.getCurrentUser() != null) {
            mockAuth.updateCurrentUserName(name)
        }
    }

    override fun getName(): String {
        val currentUser = mockAuth.getCurrentUser()
        return currentUser?.displayName ?: throw IllegalStateException("No user is logged in.")
    }
}