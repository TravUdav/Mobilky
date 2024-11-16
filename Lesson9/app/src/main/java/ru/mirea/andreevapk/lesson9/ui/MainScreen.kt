package ru.mirea.andreevapk.lesson9.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mirea.andreevapk.domain.models.Movie
import ru.mirea.andreevapk.lesson9.MainViewModel
import ru.mirea.andreevapk.lesson9.ui.mock.MockMainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel) {
    // Observe LiveData from the ViewModel
    val favoriteMovie by viewModel.favoriteMovie.observeAsState("Нет данных!")
    val movieInput = remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            // Display the current favorite movie
            Text(
                text = favoriteMovie,
                fontSize = 20.sp,
            )

            // Button to fetch the movie
            Button(onClick = {
                viewModel.getMovie() // Trigger fetching the movie from the repository
            }) {
                Text(
                    text = "Отобразить любимый фильм",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }

            // Input field for the movie name
            UnderlinedTextField(
                value = movieInput.value,
                onValueChange = { movieInput.value = it },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )

            // Button to save the movie
            Button(onClick = {
                viewModel.saveMovie(Movie(2, movieInput.value)) // Save the movie to the repository
            }) {
                Text(
                    text = "Сохранить любимый фильм",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun UnderlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Blue, // Underline color when focused
            unfocusedIndicatorColor = Color.Gray // Underline color when not focused
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    val mockViewModel = MockMainViewModel()
    MainScreen(viewModel = mockViewModel)
}