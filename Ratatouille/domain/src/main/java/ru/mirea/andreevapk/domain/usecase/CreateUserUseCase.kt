package ru.mirea.andreevapk.domain.usecase

import ru.mirea.andreevapk.domain.repository.UserRepository

class CreateUserUseCase(private val userRepository: UserRepository) {

    suspend fun execute(param: LoginParam) {
        userRepository.createUser(param.email, param.password)
    }
}