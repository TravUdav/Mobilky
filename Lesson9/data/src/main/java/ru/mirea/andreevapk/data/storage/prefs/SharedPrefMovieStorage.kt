package ru.mirea.andreevapk.data.storage.prefs

import android.content.Context
import android.content.SharedPreferences
import ru.mirea.andreevapk.data.repository.MovieStorage
import ru.mirea.andreevapk.data.storage.model.MovieLocal
import java.time.LocalDate

class SharedPrefMovieStorage(context: Context) : MovieStorage {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("Lesson9", Context.MODE_PRIVATE)
    private val KeyMovieName = "MOVIE"
    private val KeyMovieID = "IDMovie"
    private val KeyMovieDate = "DateMovie"

    override fun save(movie: MovieLocal): Boolean {
        val editor = preferences.edit()
        editor.putString(KeyMovieName, movie.name)
        editor.putString(KeyMovieDate, LocalDate.now().toString())
        editor.putInt(KeyMovieID, movie.id)
        editor.apply()
        return true
    }

    override fun get(): MovieLocal {
        val nameMovie = preferences.getString(KeyMovieName, null)
        val dateMovie = preferences.getString(KeyMovieDate, LocalDate.now().toString())
        val idMovie = preferences.getInt(KeyMovieID, 0)
        return MovieLocal(idMovie, nameMovie, dateMovie)
    }
}