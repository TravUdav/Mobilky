package ru.mirea.andreevapk.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import ru.mirea.andreevapk.domain.model.GUEST_ID
import ru.mirea.andreevapk.domain.model.GUEST_NAME
import ru.mirea.andreevapk.domain.model.User
import ru.mirea.andreevapk.domain.repository.UserRepository
import kotlin.math.absoluteValue
import kotlin.random.Random

class UserRepositoryImpl(private val firebaseAuth: FirebaseAuth) : UserRepository {

    override fun getUser(): User {
        val currentUser = firebaseAuth.currentUser
        return if (currentUser != null) {
            User(
                id = currentUser.uid.hashCode(), // Convert string ID with hash
                name = currentUser.displayName ?: GUEST_NAME
            )
        } else {
            User(
                id = GUEST_ID,
                name = GUEST_NAME
            )
        }
    }

    override suspend fun loginUser(email: String, password: String): Boolean {
        val loginSuccess: Boolean
        val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        loginSuccess = authResult.user != null
        return loginSuccess
    }

    override suspend fun createUser(email: String, password: String): Boolean {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val creationSuccess = authResult.user != null
            if (creationSuccess) {
                val user = firebaseAuth.currentUser
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName("New User ${Random.nextInt().absoluteValue}")
                    .build()
                user?.updateProfile(profileUpdates)?.await()
            }
            creationSuccess
        } catch (e: Exception) {
            false
        }
    }

    override fun logoutUser() {
        firebaseAuth.signOut()
    }

    override suspend fun setName(name: String) {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()

            currentUser.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        throw IllegalStateException("Failed to update user name.")
                    }
                }
        } else {
            throw IllegalStateException("No user is logged in.")
        }
    }

    override fun getName(): String {
        val currentUser = firebaseAuth.currentUser
        return currentUser?.displayName ?: throw IllegalStateException("No user is logged in.")
    }
}