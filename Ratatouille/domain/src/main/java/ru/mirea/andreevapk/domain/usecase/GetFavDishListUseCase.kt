package ru.mirea.andreevapk.domain.usecase

import ru.mirea.andreevapk.domain.model.Dish
import ru.mirea.andreevapk.domain.repository.FavDishRepository

class GetFavDishListUseCase(private val repository: FavDishRepository) {
    fun execute(): List<Dish> {
        return repository.getFavDishList()
    }
}