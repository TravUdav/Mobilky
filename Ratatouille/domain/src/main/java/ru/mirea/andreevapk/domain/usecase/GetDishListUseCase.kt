package ru.mirea.andreevapk.domain.usecase

import ru.mirea.andreevapk.domain.model.Dish
import ru.mirea.andreevapk.domain.repository.DishRepository

class GetDishListUseCase(private val repository: DishRepository) {
    fun execute(): List<Dish> {
        return repository.getDishList()
    }
}