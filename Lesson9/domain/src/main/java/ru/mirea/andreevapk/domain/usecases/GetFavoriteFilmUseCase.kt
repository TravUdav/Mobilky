package ru.mirea.andreevapk.domain.usecases

import ru.mirea.andreevapk.domain.models.Movie
import ru.mirea.andreevapk.domain.repository.MovieRepository

class GetFavoriteFilmUseCase(private val movieRepository: MovieRepository) {

    fun execute(): Movie {
        return movieRepository.getMovie()
    }
}