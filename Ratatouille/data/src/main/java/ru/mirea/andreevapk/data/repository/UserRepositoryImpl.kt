package ru.mirea.andreevapk.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
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

    private val userLiveData = MediatorLiveData<User>()
    private val guestUser = User(id = GUEST_ID, name = GUEST_NAME)

    init {
        val localUserSource = userDao.getUserFlow().asLiveData()
        userLiveData.addSource(localUserSource) { cachedUser ->
            if (cachedUser != null) {
                userLiveData.value = User(cachedUser.id, cachedUser.name)
            }
        }

        val firebaseUserSource = MutableLiveData<User>()
        firebaseAuth.addAuthStateListener { auth ->
            val currentUser = auth.currentUser
            firebaseUserSource.value = if (currentUser != null) {
                User(
                    id = currentUser.uid.hashCode(),
                    name = currentUser.displayName ?: GUEST_NAME
                )
            } else {
                guestUser
            }
        }
        userLiveData.addSource(firebaseUserSource) { firebaseUser ->
            if (firebaseUser.id != GUEST_ID) {
                userLiveData.value = firebaseUser
                sharedPreferencesHelper.setUserId(firebaseUser.id.toString())
            }
        }
    }

    override fun getUserLiveData(): LiveData<User> = userLiveData

    override suspend fun refreshUserFromNetwork() {
        val userId = sharedPreferencesHelper.getUserId()
        if (userId != null) {
            val networkUser = networkApi.fetchUserData(userId)
            if (networkUser.id != MOCK_API_ID) {
                userDao.insertUser(UserEntity(networkUser.id, networkUser.name ?: "Unknown"))
                userLiveData.postValue(networkUser)
            }
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
        userLiveData.value = guestUser
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