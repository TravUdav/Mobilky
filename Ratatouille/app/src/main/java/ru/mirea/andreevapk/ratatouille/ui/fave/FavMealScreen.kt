package ru.mirea.andreevapk.ratatouille.ui.fave

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavMealScreen(viewModel: FavMealViewModel) {
    val mealList by viewModel.mealList
    val feedbackMessage by viewModel.feedbackMessage

    Scaffold(
        topBar = { TopAppBar(title = { Text("Favorite Meals") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (feedbackMessage.isNotEmpty()) {
                Text(text = feedbackMessage, color = MaterialTheme.colorScheme.primary)
            }

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(mealList) { meal ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "${meal.name}: ${meal.instructions}")
                        Button(onClick = {
                            viewModel.removeFavMeal(meal.id)
                        }) {
                            Text("Remove")
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                var newMealName by remember { mutableStateOf("") }
                var newMealRecipe by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = newMealName,
                    onValueChange = { newMealName = it },
                    label = { Text("Meal Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = newMealRecipe,
                    onValueChange = { newMealRecipe = it },
                    label = { Text("Meal Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        viewModel.addFavMeal(newMealName, newMealRecipe)
                        newMealName = ""
                        newMealRecipe = ""
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Dish")
                }
            }
        }
    }
}