package ru.mirea.andreevapk.data.mock

import ru.mirea.andreevapk.domain.model.Meal
import ru.mirea.andreevapk.domain.repository.FavMealRepository

class MockFavMealRepositoryImpl : FavMealRepository {

    override fun getFavMealList(): List<Meal> {
        return mockMealList.map { MealShortToMealMapper.map(it) }
    }

    override fun addFavMeal(meal: Meal) {
        mockMealList.add(MealToMealShortMapper.map(meal))
    }

    override fun removeFavMealById(mealId: String): String {
        val meal = mockMealList.find { it.id == mealId }
        return if (meal != null) {
            mockMealList.remove(meal)
            "Meal with ID $mealId removed successfully"
        } else {
            "Meal not found"
        }
    }
}