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
import ru.mirea.andreevapk.domain.model.Dish
import ru.mirea.andreevapk.domain.usecase.AddFavDishUseCase
import ru.mirea.andreevapk.domain.usecase.GetFavDishListUseCase
import ru.mirea.andreevapk.domain.usecase.RemoveFavDishByIdUseCase
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavDishScreen(
    getFavDishListUseCase: GetFavDishListUseCase,
    addFavDishUseCase: AddFavDishUseCase,
    removeFavDishByIdUseCase: RemoveFavDishByIdUseCase
) {
    // State to manage the list of dishes
    var dishList by remember { mutableStateOf(getFavDishListUseCase.execute()) }
    var newDishName by remember { mutableStateOf("") }
    var newDishRecipe by remember { mutableStateOf("") }
    var feedbackMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Favorite Dishes") })
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
                items(dishList.size) { index ->
                    val dish = dishList[index]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "${dish.name}: ${dish.recipe}")
                        Button(onClick = {
                            feedbackMessage = removeFavDishByIdUseCase.execute(dish.id)
                            dishList = getFavDishListUseCase.execute()
                        }) {
                            Text("Remove")
                        }
                    }
                }
            }

            // Add New Dish
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = newDishName,
                    onValueChange = { newDishName = it },
                    label = { Text("Dish Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = newDishRecipe,
                    onValueChange = { newDishRecipe = it },
                    label = { Text("Dish Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        if (newDishName.isNotBlank() && newDishRecipe.isNotBlank()) {
                            val newDish = Dish(
                                id = Random.nextInt(1000, 9999),
                                name = newDishName,
                                recipe = newDishRecipe
                            )
                            addFavDishUseCase.execute(newDish)
                            dishList = getFavDishListUseCase.execute()
                            feedbackMessage = "Dish added successfully!"
                            newDishName = ""
                            newDishRecipe = ""
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