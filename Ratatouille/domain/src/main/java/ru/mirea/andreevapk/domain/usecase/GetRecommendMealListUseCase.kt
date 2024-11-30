package ru.mirea.andreevapk.domain.usecase

import ru.mirea.andreevapk.domain.model.Meal
import ru.mirea.andreevapk.domain.repository.MealRepository

class GetRecommendMealListUseCase(private val repository: MealRepository) {
    suspend fun execute(): List<Meal> {
        return listOf(repository.getRandomMeal())
    }
}
