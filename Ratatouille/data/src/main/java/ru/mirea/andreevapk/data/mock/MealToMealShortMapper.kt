package ru.mirea.andreevapk.data.mock

import ru.mirea.andreevapk.domain.model.Meal
import ru.mirea.andreevapk.domain.model.MealShort

object MealToMealShortMapper {
    fun map(meal: Meal): MealShort {
        return MealShort(
            id = meal.id,
            name = meal.name,
            instructions = meal.instructions,
            thumbnail = meal.thumbnail
        )
    }
}