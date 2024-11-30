package ru.mirea.andreevapk.ratatouille.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import ru.mirea.andreevapk.ratatouille.ui.theme.RatatouilleTheme

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RatatouilleTheme {
                MainScreen(mainViewModel)
            }
        }
    }
}