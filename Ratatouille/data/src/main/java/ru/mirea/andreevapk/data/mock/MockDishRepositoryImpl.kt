package ru.mirea.andreevapk.data.mock

import android.graphics.Bitmap
import ru.mirea.andreevapk.domain.model.Dish
import ru.mirea.andreevapk.domain.repository.DishRepository

class MockDishRepositoryImpl : DishRepository {

    override fun getDishList(): List<Dish> {
        // Return a static list of dishes for mock purposes
        return mockDishList
    }

    override fun getRecommendDishList(): List<Dish> {
        // Return a static filtered list (e.g., recommended dishes based on mock criteria)
        return mockDishList.filter { it.name?.contains("Chicken") == true }
    }

    override fun getDishFullInfo(): Dish {
        // Return full info of a mock dish (first one in the list)
        return mockDishList.first()
    }

    override fun uploadDishToDetect(image: Bitmap): Dish {
        // Simulate uploading an image and detecting a dish
        // Returning a static mock dish after detection
        return mockDishList.find { it.id == 1 } ?: mockDishList.first()  // Simulate detection of "Spaghetti Carbonara"
    }
}