package ru.mirea.andreevapk.domain.usecase

import ru.mirea.andreevapk.domain.repository.FavDishRepository

class RemoveDishByIdUseCase(private val repository: FavDishRepository) {
    fun execute(dishId: Int): String {
        return repository.removeFavDishById(dishId)
    }
}