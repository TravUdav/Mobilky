package ru.mirea.andreevapk.domain.repository

import ru.mirea.andreevapk.domain.models.Movie

interface MovieRepository {
    fun saveMovie(movie: Movie): Boolean
    fun getMovie(): Movie
}