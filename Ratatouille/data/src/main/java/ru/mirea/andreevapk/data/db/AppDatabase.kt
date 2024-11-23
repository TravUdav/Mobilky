package ru.mirea.andreevapk.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.mirea.andreevapk.data.repository.UserDao

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}