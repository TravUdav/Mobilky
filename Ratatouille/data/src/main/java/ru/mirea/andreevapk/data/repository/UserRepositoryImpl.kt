package ru.mirea.andreevapk.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import ru.mirea.andreevapk.data.db.UserEntity
import ru.mirea.andreevapk.domain.model.GUEST_ID
import ru.mirea.andreevapk.domain.model.GUEST_NAME
import ru.mirea.andreevapk.domain.model.MOCK_API_ID
import ru.mirea.andreevapk.domain.model.User
import ru.mirea.andreevapk.domain.repository.UserRepository

class UserRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val userDao: UserDao,
    private val networkApi: NetworkApi
) : UserRepository {

    override suspend fun getUser(): User {
        val userId = sharedPreferencesHelper.getUserId()
        if (userId != null) {
            val cachedUser = userDao.getUserById(userId.hashCode())
            if (cachedUser != null) {
                return User(cachedUser.id, cachedUser.name)
            }

            val networkUser = networkApi.fetchUserData(userId)
            if (networkUser.id != MOCK_API_ID) {
                userDao.insertUser(UserEntity(networkUser.id, networkUser.name ?: "Unknown"))
                return networkUser
            }
        }

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
        val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        val user = authResult.user
        if (user != null) {
            sharedPreferencesHelper.setUserId(user.uid)
            userDao.insertUser(UserEntity(user.uid.hashCode(), user.displayName ?: "Unknown"))
            return true
        }
        return false
    }

    override suspend fun createUser(email: String, password: String): Boolean {
        val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        val user = authResult.user
        if (user != null) {
            sharedPreferencesHelper.setUserId(user.uid)
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName("New User")
                .build()
            user.updateProfile(profileUpdates).await()

            userDao.insertUser(UserEntity(user.uid.hashCode(), user.displayName ?: "Unknown"))
            return true
        }
        return false
    }

    override fun logoutUser() {
        firebaseAuth.signOut()
        sharedPreferencesHelper.clearUserData()
    }

    override suspend fun setName(name: String) {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(name).build()
            currentUser.updateProfile(profileUpdates).await()

            userDao.insertUser(UserEntity(currentUser.uid.hashCode(), name))
        } else {
            throw IllegalStateException("No user is logged in.")
        }
    }

    override suspend fun getName(): String {
        val userId = sharedPreferencesHelper.getUserId()
        if (userId != null) {
            return userDao.getUserById(userId.hashCode())?.name
                ?: firebaseAuth.currentUser?.displayName
                ?: "Guest"
        }
        throw IllegalStateException("No user is logged in.")
    }
}