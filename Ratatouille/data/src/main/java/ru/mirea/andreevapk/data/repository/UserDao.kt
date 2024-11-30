package ru.mirea.andreevapk.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.mirea.andreevapk.data.db.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUserByIdFlow(userId: Int): Flow<UserEntity?>

    @Query("SELECT * FROM user LIMIT 1")
    fun getUserFlow(): Flow<UserEntity?>

    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Int): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("DELETE FROM user WHERE id = :id")
    suspend fun deleteUserById(id: Int)
}