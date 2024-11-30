package ru.mirea.andreevapk.ratatouille.ui.auth

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import ru.mirea.andreevapk.data.db.AppDatabase
import ru.mirea.andreevapk.data.repository.NetworkApi
import ru.mirea.andreevapk.data.repository.SharedPreferencesHelper
import ru.mirea.andreevapk.data.repository.UserRepositoryImpl
import ru.mirea.andreevapk.domain.model.User
import ru.mirea.andreevapk.domain.repository.UserRepository
import ru.mirea.andreevapk.domain.usecase.CreateUserUseCase
import ru.mirea.andreevapk.domain.usecase.GetUserUseCase
import ru.mirea.andreevapk.domain.usecase.LoginByEmailUseCase
import ru.mirea.andreevapk.domain.usecase.LoginParam
import ru.mirea.andreevapk.domain.usecase.LogoutUserUseCase
import ru.mirea.andreevapk.domain.usecase.SetUserNameUseCase


class AuthViewModel(
    application: Application,
    sharedPreferences: SharedPreferences
) : AndroidViewModel(application) {

    private val sharedPreferencesHelper = SharedPreferencesHelper(sharedPreferences)
    private val database =
        Room.databaseBuilder(application, AppDatabase::class.java, "app_database").build()
    private val userDao = database.userDao()
    private val networkApi = NetworkApi()
    private val firebaseAuth = FirebaseAuth.getInstance()

    private val userRepository: UserRepository = UserRepositoryImpl(
        firebaseAuth = firebaseAuth,
        sharedPreferencesHelper = sharedPreferencesHelper,
        userDao = userDao,
        networkApi = networkApi
    )

    val userLiveData: LiveData<User> = GetUserUseCase(userRepository).observeUser()

    val setUserNameUseCase = SetUserNameUseCase(userRepository)
    val logoutUserUseCase = LogoutUserUseCase(userRepository)
    val loginByEmailUseCase = LoginByEmailUseCase(userRepository)
    val createUserUseCase = CreateUserUseCase(userRepository)

    fun refreshUser() {
        viewModelScope.launch {
            GetUserUseCase(userRepository).refreshUser()
        }
    }

    fun updateUserName(name: String) {
        viewModelScope.launch {
            setUserNameUseCase.execute(name)
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUserUseCase.execute()
        }
    }

    fun login(email: String, password: String, onLogin: () -> Unit) {
        viewModelScope.launch {
            loginByEmailUseCase.execute(LoginParam(email, password))
            onLogin()
        }
    }

    fun createUser(email: String, password: String, onLogin: () -> Unit) {
        viewModelScope.launch {
            createUserUseCase.execute(LoginParam(email, password))
            onLogin()
        }
    }
}
