package ru.mirea.andreevapk.data.repository

import android.content.SharedPreferences

class SharedPreferencesHelper(private val sharedPreferences: SharedPreferences) {

    fun getUserId(): String? = sharedPreferences.getString(USER_ID, null)

    fun setUserId(userId: String) {
        sharedPreferences.edit().putString(USER_ID, userId).apply()
    }

    fun clearUserData() {
        sharedPreferences.edit().clear().apply()
    }

    companion object{
        private const val USER_ID = "USER_ID"
    }
}