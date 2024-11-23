package ru.mirea.andreevapk.domain.usecase

import ru.mirea.andreevapk.domain.repository.UserRepository

class LoginByEmailUseCase(private val repository: UserRepository) {
    suspend fun  execute(param: LoginParam) {
        repository.loginUser(param.email, param.password)
    }
}

data class LoginParam(
    val email: String,
    val password: String
)