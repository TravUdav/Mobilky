package ru.mirea.andreevapk.domain.usecase

import ru.mirea.andreevapk.domain.model.Dish
import ru.mirea.andreevapk.domain.repository.FavDishRepository

class AddDishUseCase(private val repository: FavDishRepository) {
    fun execute(dish: Dish) {
        repository.addFavDish(dish)
    }
}