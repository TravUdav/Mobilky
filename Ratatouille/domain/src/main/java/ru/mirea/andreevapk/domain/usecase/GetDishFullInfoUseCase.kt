package ru.mirea.andreevapk.domain.usecase

import ru.mirea.andreevapk.domain.model.Dish
import ru.mirea.andreevapk.domain.repository.DishRepository

class GetDishFullInfoUseCase(private val repository: DishRepository) {
    fun execute(): Dish {
        return repository.getDishFullInfo()
    }
}