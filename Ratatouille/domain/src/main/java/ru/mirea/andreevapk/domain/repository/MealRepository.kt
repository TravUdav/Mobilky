package ru.mirea.andreevapk.domain.repository

import android.graphics.Bitmap
import ru.mirea.andreevapk.domain.model.Category
import ru.mirea.andreevapk.domain.model.Meal

interface MealRepository {
    suspend fun getRandomMeal(): Meal
    suspend fun getCategories(): List<Category>
    suspend fun uploadMealToDetect(image: Bitmap): Meal
    suspend fun searchMealByName(mealName: String): List<Meal>
}