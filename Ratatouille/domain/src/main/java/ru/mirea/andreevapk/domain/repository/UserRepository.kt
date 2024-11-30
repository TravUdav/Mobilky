package ru.mirea.andreevapk.domain.repository

import androidx.lifecycle.LiveData
import ru.mirea.andreevapk.domain.model.User


interface UserRepository {
    fun getUserLiveData(): LiveData<User>
    suspend fun refreshUserFromNetwork()
    suspend fun loginUser(email: String, password: String): Boolean
    suspend fun createUser(email: String, password: String): Boolean
    fun logoutUser()
    suspend fun setName(name: String)
    suspend fun getName(): String
}