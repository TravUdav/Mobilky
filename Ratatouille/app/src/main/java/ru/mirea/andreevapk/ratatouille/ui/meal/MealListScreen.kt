package ru.mirea.andreevapk.ratatouille.ui.meal

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ru.mirea.andreevapk.domain.model.Meal
import ru.mirea.andreevapk.domain.usecase.GetMealListUseCase

@Composable
fun MealListScreen(getMealListUseCase: GetMealListUseCase) {
    val mealListState = produceState<List<Meal>>(initialValue = emptyList()) {
        value = getMealListUseCase.execute("A")
    }

    val mealList = mealListState.value
    var selectedMeal by remember { mutableStateOf<Meal?>(null) }
    val favorites = remember { mutableStateOf<Set<String>>(setOf()) }

    BackHandler(enabled = selectedMeal != null) {
        selectedMeal = null
    }

    fun onMealClick(meal: Meal) {
        selectedMeal = meal
    }

    fun onFavClick(meal: Meal) {
        if (favorites.value.contains(meal.id)) {
            favorites.value -= meal.id
        } else {
            favorites.value += meal.id
        }
    }

    fun onHomeClick() {
        selectedMeal = null
    }

    selectedMeal?.let {
        MealFullInfoScreen(
            meal = it,
            onFavoriteClick = ::onFavClick,
            onHomeClick = ::onHomeClick,
            isFavorite = favorites.value.contains(it.id)
        )
    } ?: run {
        MealListContent(
            mealList = mealList,
            onMealClick = ::onMealClick,
            onFavClick = ::onFavClick,
            favorites = favorites.value
        )
    }
}