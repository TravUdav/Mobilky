package ru.mirea.andreevapk.ratatouille

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import ru.mirea.andreevapk.data.repository.UserRepositoryImpl
import ru.mirea.andreevapk.domain.usecase.CreateUserUseCase
import ru.mirea.andreevapk.domain.usecase.GetUserUseCase
import ru.mirea.andreevapk.domain.usecase.LoginByEmailUseCase
import ru.mirea.andreevapk.domain.usecase.LogoutUserUseCase
import ru.mirea.andreevapk.domain.usecase.SetUserNameUseCase
import ru.mirea.andreevapk.ratatouille.ui.FirebaseUserManagementScreen


val userRepository = UserRepositoryImpl(FirebaseAuth.getInstance())

val getUserUseCase = GetUserUseCase(userRepository)
val setUserNameUseCase = SetUserNameUseCase(userRepository)
val logoutUserUseCase = LogoutUserUseCase(userRepository)
val loginByEmailUseCase = LoginByEmailUseCase(userRepository)
val createUserUseCase = CreateUserUseCase(userRepository)

class AuthActivity : ComponentActivity() {


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
            FirebaseUserManagementScreen(
                lifecycleScope = lifecycleScope,
                getUserUseCase = getUserUseCase,
                setUserNameUseCase = setUserNameUseCase,
                logoutUserUseCase = logoutUserUseCase,
                loginByEmailUseCase = loginByEmailUseCase,
                createUserUseCase = createUserUseCase
            )
        }
    }
}