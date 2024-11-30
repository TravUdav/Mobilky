package ru.mirea.andreevapk.data.repository

import ru.mirea.andreevapk.data.storage.model.MovieLocal
import ru.mirea.andreevapk.domain.models.Movie
import ru.mirea.andreevapk.domain.repository.MovieRepository
import java.time.LocalDate
import kotlin.random.Random

class MovieRepositoryImpl(private val movieStorage: MovieStorage) : MovieRepository {

    override fun saveMovie(movie: Movie): Boolean {
        movieStorage.save(mapToStorage(movie))
        return true
    }

    override fun getMovie(): Movie {
        return mapToDomain(movieStorage.get())
    }

    private fun mapToStorage(movie: Movie): MovieLocal {
        return MovieLocal(Random.nextInt(), movie.name, LocalDate.now().toString())
    }

    private fun mapToDomain(movie: MovieLocal): Movie {
        return Movie(
            id = movie.id,
            name = movie.name
        )
    }
}