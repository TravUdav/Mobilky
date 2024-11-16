package ru.mirea.andreevapk.domain.repository

import android.graphics.Bitmap
import ru.mirea.andreevapk.domain.model.Dish


interface DishRepository {
    fun getDishList(): List<Dish>
    fun getRecommendDishList(): List<Dish>
    fun getDishFullInfo(): Dish
    fun uploadDishToDetect(image: Bitmap): Dish
}