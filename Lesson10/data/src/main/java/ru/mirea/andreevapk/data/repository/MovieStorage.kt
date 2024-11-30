package ru.mirea.andreevapk.data.repository

import ru.mirea.andreevapk.data.storage.model.MovieLocal

interface MovieStorage {
    fun get(): MovieLocal
    fun save(movie: MovieLocal): Boolean
}