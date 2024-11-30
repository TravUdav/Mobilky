package ru.mirea.andreevapk.ratatouille.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import ru.mirea.andreevapk.domain.usecase.AddFavMealUseCase
import ru.mirea.andreevapk.domain.usecase.GetFavMealListUseCase
import ru.mirea.andreevapk.domain.usecase.RemoveFavMealByIdUseCase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavMealScreen(
    getFavMealListUseCase: GetFavMealListUseCase,
    addFavMealUseCase: AddFavMealUseCase,
    removeFavMealByIdUseCase: RemoveFavMealByIdUseCase
) {
    // State to manage the list of dishes
    var mealList by remember { mutableStateOf(getFavMealListUseCase.execute()) }
    var newMealName by remember { mutableStateOf("") }
    var newMealRecipe by remember { mutableStateOf("") }
    var feedbackMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Favorite Meals") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Feedback message
            if (feedbackMessage.isNotEmpty()) {
                Text(text = feedbackMessage, color = MaterialTheme.colorScheme.primary)
            }

            // List of Dishes
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(mealList.size) { index ->
                    val meal = mealList[index]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "${meal.name}: ${meal.instructions}")
                        Button(onClick = {
                            feedbackMessage = removeFavMealByIdUseCase.execute(meal.id)
                            mealList = getFavMealListUseCase.execute()
                        }) {
                            Text("Remove")
                        }
                    }
                }
            }

            // Add New Dish
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
                        if (newMealName.isNotBlank() && newMealRecipe.isNotBlank()) {
//                            val newDish = MealShort(
//                                id = Random.nextInt(1000, 9999).toString(),
//                                name = newMealName,
//                                instructions = newMealRecipe
//                            )
//                            addFavMealUseCase.execute(newDish)
                            mealList = getFavMealListUseCase.execute()
                            feedbackMessage = "Dish added successfully!"
                            newMealName = ""
                            newMealRecipe = ""
                        } else {
                            feedbackMessage = "Please fill in both fields."
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Dish")
                }
            }
        }
    }
}