package ru.mirea.andreevapk.ratatouille.ui.meal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.mirea.andreevapk.domain.model.Meal
import ru.mirea.andreevapk.ratatouille.ui.main.MealMainItem

@Composable
fun MealListContent(
    mealList: List<Meal>,
    onMealClick: (Meal) -> Unit,
    onFavClick: (Meal) -> Unit,
    favorites: Set<String>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(mealList) { meal ->
                MealMainItem(
                    meal = meal,
                    onMealClick = onMealClick,
                    onFavoriteClick = onFavClick,
                    isFavorite = favorites.contains(meal.id)
                )
            }
        }
    }
}