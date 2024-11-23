package ru.mirea.andreevapk.data.repository

import ru.mirea.andreevapk.domain.model.MOCK_API_ID
import ru.mirea.andreevapk.domain.model.User

class NetworkApi {
    fun fetchUserData(userId: String): User {
        return User(id = MOCK_API_ID, name = "Mock fetched User")
    }
}