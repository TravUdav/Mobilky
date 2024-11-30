package ru.mirea.andreevapk.domain.usecase

import androidx.lifecycle.LiveData
import ru.mirea.andreevapk.domain.model.User
import ru.mirea.andreevapk.domain.repository.UserRepository

class GetUserUseCase(private val repository: UserRepository) {
    fun observeUser(): LiveData<User> = repository.getUserLiveData()

    suspend fun refreshUser() {
        repository.refreshUserFromNetwork()
    }
}