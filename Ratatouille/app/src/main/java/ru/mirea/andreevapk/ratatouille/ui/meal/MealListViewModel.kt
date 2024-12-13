package ru.mirea.andreevapk.ratatouille.ui.meal

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mirea.andreevapk.domain.model.Meal
import ru.mirea.andreevapk.domain.usecase.GetMealListUseCase

class MealListViewModel(
    private val getMealListUseCase: GetMealListUseCase
) : ViewModel() {

    private val _mealList = mutableStateOf<List<Meal>>(emptyList())
    val mealList: State<List<Meal>> = _mealList

    private val _favorites = mutableStateOf<Set<String>>(setOf())
    val favorites: State<Set<String>> = _favorites

    private val _selectedMeal = mutableStateOf<Meal?>(null)
    val selectedMeal: State<Meal?> = _selectedMeal

    fun fetchMealList() {
        viewModelScope.launch {
            _mealList.value = getMealListUseCase.execute("A")
        }
    }

    fun selectMeal(meal: Meal) {
        _selectedMeal.value = meal
    }

    fun toggleFavorite(meal: Meal) {
        _favorites.value = if (_favorites.value.contains(meal.id)) {
            _favorites.value - meal.id
        } else {
            _favorites.value + meal.id
        }
    }

    fun deselectMeal() {
        _selectedMeal.value = null
    }

    init {
        fetchMealList()
    }
}
