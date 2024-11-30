package ru.mirea.andreevapk.lesson10.ui.mock

import ru.mirea.andreevapk.data.mock.MockMovieRepository
import ru.mirea.andreevapk.lesson10.MainViewModel

class MockMainViewModel : MainViewModel(MockMovieRepository()) {
    init {
        favoriteMovie.value = "Mock Favorite Movie"
    }
}