package ru.mirea.andreevapk.lesson10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import ru.mirea.andreevapk.data.repository.MovieRepositoryImpl
import ru.mirea.andreevapk.data.storage.prefs.SharedPrefMovieStorage
import ru.mirea.andreevapk.lesson10.ui.MainScreen
import ru.mirea.andreevapk.lesson10.ui.theme.MovieProjectTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.ViewModelFactory(MovieRepositoryImpl(SharedPrefMovieStorage(this)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieProjectTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}