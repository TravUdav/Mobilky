package ru.mirea.andreevapk.ratatouille.ui.meal

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ru.mirea.andreevapk.domain.model.Meal
import ru.mirea.andreevapk.domain.usecase.GetMealListUseCase

@Composable
fun MealListScreen(viewModel: MealListViewModel) {
    val mealListState by viewModel.mealList
    val favorites by viewModel.favorites
    val selectedMeal by viewModel.selectedMeal

    val listState = rememberLazyListState()

    BackHandler(enabled = selectedMeal != null) {
        viewModel.deselectMeal()
    }

    fun onMealClick(meal: Meal) {
        viewModel.selectMeal(meal)
    }

    fun onFavClick(meal: Meal) {
        viewModel.toggleFavorite(meal)
    }

    fun onHomeClick() {
        viewModel.deselectMeal()
    }

    selectedMeal?.let {
        MealFullInfoScreen(
            meal = it,
            onFavoriteClick = ::onFavClick,
            onHomeClick = ::onHomeClick,
            isFavorite = favorites.contains(it.id)
        )
    } ?: run {
        MealListContent(
            mealList = mealListState,
            onMealClick = ::onMealClick,
            onFavClick = ::onFavClick,
            favorites = favorites,
            listState = listState
        )
    }
}