package ru.mirea.andreevapk.domain.repository

import ru.mirea.andreevapk.domain.model.User


interface UserRepository {
    fun getUser(): User
    suspend fun loginUser(email: String, password: String): Boolean
    suspend fun createUser(email: String, password: String): Boolean
    fun logoutUser()
    suspend fun setName(name: String)
    fun getName(): String
}