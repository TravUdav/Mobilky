package ru.mirea.andreevapk.domain.usecase

import ru.mirea.andreevapk.domain.repository.FavDishRepository

class RemoveFavDishByIdUseCase(private val repository: FavDishRepository) {
    fun execute(dishId: Int): String {
        return repository.removeFavDishById(dishId)
    }
}