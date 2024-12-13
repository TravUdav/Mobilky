package ru.mirea.andreevapk.ratatouille.ui.fave

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mirea.andreevapk.domain.model.Meal
import ru.mirea.andreevapk.domain.model.MealShort
import ru.mirea.andreevapk.domain.usecase.AddFavMealUseCase
import ru.mirea.andreevapk.domain.usecase.GetFavMealListUseCase
import ru.mirea.andreevapk.domain.usecase.RemoveFavMealByIdUseCase

class FavMealViewModel(
    private val getFavMealListUseCase: GetFavMealListUseCase,
    private val addFavMealUseCase: AddFavMealUseCase,
    private val removeFavMealByIdUseCase: RemoveFavMealByIdUseCase
) : ViewModel() {

    private val _mealList = mutableStateOf<List<Meal>>(emptyList())
    val mealList: State<List<Meal>> = _mealList

    private val _feedbackMessage = mutableStateOf("")
    val feedbackMessage: State<String> = _feedbackMessage

    fun fetchFavMeals() {
        viewModelScope.launch {
            _mealList.value = getFavMealListUseCase.execute()
        }
    }

    fun removeFavMeal(mealId: String) {
        viewModelScope.launch {
            val message = removeFavMealByIdUseCase.execute(mealId)
            _feedbackMessage.value = message
            fetchFavMeals() // Re-fetch the updated list of favorite meals
        }
    }

    fun addFavMeal(mealName: String, mealRecipe: String) {
        if (mealName.isNotBlank() && mealRecipe.isNotBlank()) {
            viewModelScope.launch {
                val newMeal = MealShort(
                    id = generateRandomId(),
                    name = mealName,
                    instructions = mealRecipe
                )
//                addFavMealUseCase.execute(newMeal)
                _feedbackMessage.value = "Dish added successfully!"
                fetchFavMeals()
            }
        } else {
            _feedbackMessage.value = "Please fill in both fields."
        }
    }

    private fun generateRandomId(): String {
        return (1000..9999).random().toString()
    }

    init {
        fetchFavMeals()
    }
}
