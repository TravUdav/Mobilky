package ru.mirea.andreevapk.domain.usecases

import ru.mirea.andreevapk.domain.models.Movie
import ru.mirea.andreevapk.domain.repository.MovieRepository

class SaveFilmToFavoriteUseCase(private val movieRepository: MovieRepository) {
    fun execute(movie: Movie): Boolean {
        return movieRepository.saveMovie(movie)
    }
}