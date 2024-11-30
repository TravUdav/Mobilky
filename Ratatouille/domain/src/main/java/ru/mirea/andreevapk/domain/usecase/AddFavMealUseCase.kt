package ru.mirea.andreevapk.domain.usecase

import ru.mirea.andreevapk.domain.model.Meal
import ru.mirea.andreevapk.domain.repository.FavMealRepository

class AddFavMealUseCase(private val repository: FavMealRepository) {
    fun execute(meal: Meal) {
        repository.addFavMeal(meal)
    }
}