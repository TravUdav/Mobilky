package ru.mirea.andreevapk.domain.usecase

import ru.mirea.andreevapk.domain.model.Meal
import ru.mirea.andreevapk.domain.repository.MealRepository

class GetMealListUseCase(private val repository: MealRepository) {
    suspend fun execute(mealName: String): List<Meal> {
        return repository.searchMealByName(mealName)
    }
}