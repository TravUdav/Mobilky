package ru.mirea.andreevapk.lesson9.ui.mock

import ru.mirea.andreevapk.data.mock.MockMovieRepository
import ru.mirea.andreevapk.lesson9.MainViewModel

class MockMainViewModel : MainViewModel(MockMovieRepository()) {
    init {
        favoriteMovie.value = "Mock Favorite Movie"
    }
}