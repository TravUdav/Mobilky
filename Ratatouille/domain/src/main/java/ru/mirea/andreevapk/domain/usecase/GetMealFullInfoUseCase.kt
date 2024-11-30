package ru.mirea.andreevapk.domain.usecase

import ru.mirea.andreevapk.domain.model.Meal
import ru.mirea.andreevapk.domain.repository.MealRepository

class GetMealFullInfoUseCase(private val repository: MealRepository) {
    suspend fun execute(): Meal {
        return repository.getRandomMeal()
    }
}