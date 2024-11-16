package ru.mirea.andreevapk.domain.usecase

import ru.mirea.andreevapk.domain.repository.UserRepository

class LogoutUserUseCase(private val repository: UserRepository) {
    fun execute() {
        repository.logoutUser()
    }
}