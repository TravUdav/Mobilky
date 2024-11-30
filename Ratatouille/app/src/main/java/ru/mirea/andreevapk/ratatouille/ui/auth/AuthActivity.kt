package ru.mirea.andreevapk.ratatouille.ui.auth

import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mirea.andreevapk.ratatouille.ui.main.MainActivity

class AuthActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(
            application = application,
            sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@AuthActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
        })

        setContent {
            UserProfileScreen(authViewModel)
        }
    }
}

class AuthViewModelFactory(
    private val application: Application,
    private val sharedPreferences: SharedPreferences
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(application, sharedPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}