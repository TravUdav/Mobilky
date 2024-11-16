package ru.mirea.andreevapk.domain.repository

import ru.mirea.andreevapk.domain.model.Dish


interface FavDishRepository {
    fun getFavDishList(): List<Dish>
    fun addFavDish(dish: Dish)
    fun removeFavDishById(dishId: Int): String
}