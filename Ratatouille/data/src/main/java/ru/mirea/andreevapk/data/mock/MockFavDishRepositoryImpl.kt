package ru.mirea.andreevapk.data.mock

import ru.mirea.andreevapk.domain.model.Dish
import ru.mirea.andreevapk.domain.repository.FavDishRepository

class MockFavDishRepositoryImpl : FavDishRepository {

    override fun getFavDishList(): List<Dish> {
        return mockDishList
    }

    override fun addFavDish(dish: Dish) {
        mockDishList.add(dish)
    }

    override fun removeFavDishById(dishId: Int): String {
        val dish = mockDishList.find { it.id == dishId }
        return if (dish != null) {
            mockDishList.remove(dish)
            "Dish with ID $dishId removed successfully"
        } else {
            "Dish not found"
        }
    }
}