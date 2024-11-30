package ru.mirea.andreevapk.data.mock

import ru.mirea.andreevapk.domain.model.Meal
import ru.mirea.andreevapk.domain.model.MealShort

object MealShortToMealMapper {
    fun map(mealShort: MealShort): Meal {
        return Meal(
            id = mealShort.id,
            name = mealShort.name,
            category = null, // Default value
            area = null, // Default value
            instructions = mealShort.instructions,
            thumbnail = mealShort.thumbnail,
            tags = emptyList(), // Default value
            youtubeLink = null, // Default value
            ingredients = listOf() // Default value
        )
    }
}