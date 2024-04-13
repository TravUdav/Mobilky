package ru.mirea.andreevapk.employeedb

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.mirea.andreevapk.employeedb.EmployeeDao
import ru.mirea.andreevapk.employeedb.Employee

@Database(entities = [Employee::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao?
}