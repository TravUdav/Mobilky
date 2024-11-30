package ru.mirea.andreevapk.data.mappers

import ru.mirea.andreevapk.data.model.MealData
import ru.mirea.andreevapk.domain.model.Meal

object MealMapper {
    fun mapToDomain(mealData: MealData): Meal {
        return Meal(
            id = mealData.idMeal,
            name = mealData.strMeal,
            category = mealData.strCategory,
            area = mealData.strArea,
            instructions = mealData.strInstructions,
            thumbnail = mealData.strMealThumb,
            tags = mealData.strTags?.split(",")?.map { it.trim() },
            youtubeLink = mealData.strYoutube,
            ingredients = extractIngredients(mealData)
        )
    }

    private fun extractIngredients(mealData: MealData): List<String> {
        // Extract non-null, non-blank ingredients
        return listOfNotNull(
            mealData.strIngredient1,
            mealData.strIngredient2,
            mealData.strIngredient3
            // Add other ingredients if needed
        ).filter { it.isNotBlank() }
    }
}