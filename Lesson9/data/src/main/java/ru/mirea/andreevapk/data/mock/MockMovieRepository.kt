package ru.mirea.andreevapk.data.mock

import ru.mirea.andreevapk.domain.models.Movie
import ru.mirea.andreevapk.domain.repository.MovieRepository

class MockMovieRepository : MovieRepository {
    override fun saveMovie(movie: Movie): Boolean {
        return true
    }

    override fun getMovie(): Movie {
        return Movie(1, "Mock Favorite Movie")
    }
}