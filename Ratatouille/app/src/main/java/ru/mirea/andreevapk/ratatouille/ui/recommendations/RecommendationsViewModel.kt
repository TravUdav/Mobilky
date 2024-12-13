package ru.mirea.andreevapk.ratatouille.ui.recommendations

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mirea.andreevapk.domain.model.Meal
import ru.mirea.andreevapk.domain.usecase.GetRecommendMealListUseCase

class RecommendationsViewModel(
    private val getRecommendMealListUseCase: GetRecommendMealListUseCase
) : ViewModel() {

    // Holds the list of recommended meals
    private val _recommendedDishes = mutableStateOf<List<Meal>>(emptyList())
    val recommendedDishes: State<List<Meal>> = _recommendedDishes

    // Fetch the recommended meal list
    fun fetchRecommendedMeals() {
        viewModelScope.launch {
            _recommendedDishes.value = getRecommendMealListUseCase.execute()
        }
    }

    // Initialize the ViewModel by fetching meals
    init {
        fetchRecommendedMeals()
    }
}
