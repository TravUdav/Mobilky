package ru.mirea.andreevapk.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.mirea.andreevapk.data.model.CategoryResponse
import ru.mirea.andreevapk.data.model.MealResponse

interface TheMealDbApi {
    // Get a random meal
    @GET("random.php")
    suspend fun getRandomMeal(): MealResponse

    // Search for a meal by name
    @GET("search.php")
    suspend fun searchMealByName(@Query("s") mealName: String): MealResponse

    // Lookup a meal by ID
    @GET("lookup.php")
    suspend fun getMealById(@Query("i") id: String): MealResponse

    // Filter meals by category
    @GET("filter.php")
    suspend fun filterByCategory(@Query("c") category: String): MealResponse

    // List all categories
    @GET("categories.php")
    suspend fun getAllCategories(): CategoryResponse
}

