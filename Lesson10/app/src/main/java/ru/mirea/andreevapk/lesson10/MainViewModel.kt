package ru.mirea.andreevapk.lesson10

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mirea.andreevapk.domain.models.Movie
import ru.mirea.andreevapk.domain.repository.MovieRepository
import ru.mirea.andreevapk.domain.usecases.GetFavoriteFilmUseCase
import ru.mirea.andreevapk.domain.usecases.SaveFilmToFavoriteUseCase

open class MainViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    val favoriteMovie: MutableLiveData<String> = MutableLiveData()

    fun saveMovie(movie: Movie) {
        val result: Boolean = SaveFilmToFavoriteUseCase(movieRepository).execute(movie)
        favoriteMovie.value = if (result) "Фильм успешно сохранен!" else "Не удалось сохранить фильм."
    }

    fun getMovie() {
        val movie: Movie = GetFavoriteFilmUseCase(movieRepository).execute()
        favoriteMovie.value = movie.name ?: "Фильм не найден"
    }

    class ViewModelFactory(private val movieRepository: MovieRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(movieRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}