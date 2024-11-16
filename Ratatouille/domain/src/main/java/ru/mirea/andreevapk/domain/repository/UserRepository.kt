package ru.mirea.andreevapk.domain.repository

import ru.mirea.andreevapk.domain.model.User


interface UserRepository {
    fun getUser(): User
    fun logoutUser()
    fun setName(name: String)
    fun getName(): String
}