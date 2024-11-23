package ru.mirea.andreevapk.domain.usecase

import ru.mirea.andreevapk.domain.repository.UserRepository

class SetUserNameUseCase(private val repository: UserRepository) {
    suspend fun execute(newName: String) {
        repository.setName(newName)
    }
}