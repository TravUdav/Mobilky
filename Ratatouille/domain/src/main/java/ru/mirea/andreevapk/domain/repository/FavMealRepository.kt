package ru.mirea.andreevapk.domain.repository

import ru.mirea.andreevapk.domain.model.Meal


interface FavMealRepository {
    fun getFavMealList(): List<Meal>
    fun addFavMeal(meal: Meal)
    fun removeFavMealById(mealId: String): String
}