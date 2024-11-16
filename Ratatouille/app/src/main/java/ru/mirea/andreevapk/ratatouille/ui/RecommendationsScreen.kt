package ru.mirea.andreevapk.ratatouille.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.mirea.andreevapk.domain.model.Dish
import ru.mirea.andreevapk.domain.usecase.GetRecommendDishListUseCase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationsScreen(getRecommendDishListUseCase: GetRecommendDishListUseCase) {
    val recommendedDishes = remember { mutableStateOf<List<Dish>>(emptyList()) }

    LaunchedEffect(Unit) {
        recommendedDishes.value = getRecommendDishListUseCase.execute()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Recommendations") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (recommendedDishes.value.isEmpty()) {
                Text(
                    text = "No recommended dishes available",
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(recommendedDishes.value) { dish ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = dish.name ?: "Unnamed Dish", // Ensure the name is not null
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}