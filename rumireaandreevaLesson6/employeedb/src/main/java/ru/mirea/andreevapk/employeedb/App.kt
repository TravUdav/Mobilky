package ru.mirea.andreevapk.employeedb

import android.app.Application
import androidx.room.Room.databaseBuilder
import ru.mirea.andreevapk.employeedb.AppDatabase


class App : Application() {

    private lateinit var database: AppDatabase

    companion object {
        private lateinit var instance: App

        fun getInstance(): App =
            instance
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        database = databaseBuilder(this, AppDatabase::class.java, "database")
            .allowMainThreadQueries()
            .build()
    }

    fun getDatabase(): AppDatabase =
        database
}