package ru.mirea.andreevapk.lesson10.ui

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
import ru.mirea.andreevapk.lesson10.MainViewModel
import ru.mirea.andreevapk.lesson10.ui.mock.MockMainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel) {
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
            Text(
                text = favoriteMovie,
                fontSize = 20.sp,
            )

            Button(onClick = {
                viewModel.getMovie()
            }) {
                Text(
                    text = "Отобразить любимый фильм",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }

            UnderlinedTextField(
                value = movieInput.value,
                onValueChange = { movieInput.value = it },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )

            Button(onClick = {
                viewModel.saveMovie(Movie(2, movieInput.value))
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
            focusedIndicatorColor = Color.Blue, 
            unfocusedIndicatorColor = Color.Gray
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    val mockViewModel = MockMainViewModel()
    MainScreen(viewModel = mockViewModel)
}