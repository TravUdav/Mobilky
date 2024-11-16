package ru.mirea.andreevapk.domain.usecase

import ru.mirea.andreevapk.domain.model.User
import ru.mirea.andreevapk.domain.repository.UserRepository

class GetUserUseCase(private val repository: UserRepository) {
    fun execute(): User {
        return repository.getUser()
    }
}