package ru.mirea.andreevapk.domain.usecase

import ru.mirea.andreevapk.domain.model.Meal
import ru.mirea.andreevapk.domain.repository.FavMealRepository

class GetFavMealListUseCase(private val repository: FavMealRepository) {
    fun execute(): List<Meal> {
        return repository.getFavMealList()
    }
}