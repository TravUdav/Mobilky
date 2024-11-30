package ru.mirea.andreevapk.domain.usecase

import ru.mirea.andreevapk.domain.repository.FavMealRepository

class RemoveFavMealByIdUseCase(private val repository: FavMealRepository) {
    fun execute(mealId: String): String {
        return repository.removeFavMealById(mealId)
    }
}