package ru.mirea.andreevapk.data.repository

import android.graphics.Bitmap
import ru.mirea.andreevapk.data.api.TheMealDbApi
import ru.mirea.andreevapk.data.mappers.CategoryMapper
import ru.mirea.andreevapk.data.mappers.MealMapper
import ru.mirea.andreevapk.domain.model.Category
import ru.mirea.andreevapk.domain.model.Meal
import ru.mirea.andreevapk.domain.repository.MealRepository

class MealRepositoryImpl(private val api: TheMealDbApi) : MealRepository {
    override suspend fun getRandomMeal(): Meal {
        val response = api.getRandomMeal()
        val mealData = response.mealData?.firstOrNull() ?: throw Exception("No meals found")
        return MealMapper.mapToDomain(mealData)
    }

    override suspend fun searchMealByName(mealName: String): List<Meal> {
        val response = api.searchMealByName(mealName)
        val mealsData = response.mealData?.map { MealMapper.mapToDomain(it) } ?: emptyList()
        return mealsData
    }

    override suspend fun getCategories(): List<Category> {
        val response = api.getAllCategories()
        val categoryData = response.categories ?: throw Exception("No categories found")
        return categoryData.map { CategoryMapper.mapToDomain(it) }
    }

    override suspend fun uploadMealToDetect(image: Bitmap): Meal {
        return Meal(id = "-1", name = "Test", ingredients = emptyList())
    }
}