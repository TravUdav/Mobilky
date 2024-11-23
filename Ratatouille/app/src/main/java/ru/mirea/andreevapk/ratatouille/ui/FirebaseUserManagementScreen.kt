package ru.mirea.andreevapk.ratatouille.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.launch
import ru.mirea.andreevapk.domain.model.GUEST_ID
import ru.mirea.andreevapk.domain.model.User
import ru.mirea.andreevapk.domain.usecase.CreateUserUseCase
import ru.mirea.andreevapk.domain.usecase.GetUserUseCase
import ru.mirea.andreevapk.domain.usecase.LoginByEmailUseCase
import ru.mirea.andreevapk.domain.usecase.LoginParam
import ru.mirea.andreevapk.domain.usecase.LogoutUserUseCase
import ru.mirea.andreevapk.domain.usecase.SetUserNameUseCase
import ru.mirea.andreevapk.ratatouille.R

@Composable
fun FirebaseUserManagementScreen(
    lifecycleScope: LifecycleCoroutineScope,
    getUserUseCase: GetUserUseCase,
    setUserNameUseCase: SetUserNameUseCase,
    logoutUserUseCase: LogoutUserUseCase,
    loginByEmailUseCase: LoginByEmailUseCase,
    createUserUseCase: CreateUserUseCase
) {
    var user by remember { mutableStateOf<User?>(null) }
    var isSignedIn by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }

    val onLogin: () -> Unit = {
        lifecycleScope.launch {
            user = getUserUseCase.execute()
            isSignedIn = user?.id != GUEST_ID
            userName = user?.name ?: ""
        }
    }

    LaunchedEffect(Unit) {
        onLogin()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.mipmap.ic_firebase_foreground),
            contentDescription = stringResource(id = R.string.firebase_user_management),
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = stringResource(id = R.string.emailpassword_title_text),
            textAlign = TextAlign.Center,
            color = Color(0xFF4A4650),
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = if (isSignedIn) stringResource(id = R.string.signed_in) else stringResource(id = R.string.signed_out),
            textAlign = TextAlign.Center,
            color = Color(0xFF242327),
            modifier = Modifier.fillMaxWidth()
        )

        if (isSignedIn) {
            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("User Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    lifecycleScope.launch {
                        setUserNameUseCase.execute(userName)
                        user = user?.copy(name = userName)
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Update Name")
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .background(Color(0xFFCAC8CB))
                .padding(16.dp)
        ) {
            if (isSignedIn) {
                Button(
                    onClick = {
                        logoutUserUseCase.execute()
                        user = null
                        isSignedIn = false
                    },
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(text = stringResource(id = R.string.sign_out))
                }
            } else {
                SignInSection(
                    lifecycleScope,
                    email,
                    { email = it },
                    password,
                    { password = it },
                    loginByEmailUseCase,
                    createUserUseCase,
                    onLogin
                )
            }
        }
    }
}

@Composable
fun SignInSection(
    lifecycleScope: LifecycleCoroutineScope,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    loginByEmailUseCase: LoginByEmailUseCase,
    createUserUseCase: CreateUserUseCase,
    onLogin: () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(modifier = Modifier.weight(0.5f)) {
            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    lifecycleScope.launch {
                        isLoading = true
                        try {
                            loginByEmailUseCase.execute(
                                LoginParam(
                                    email = email,
                                    password = password
                                )
                            )
                            onLogin()
                        } finally {
                            isLoading = false
                        }
                    }
                },
                enabled = !isLoading,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                } else {
                    Text(text = stringResource(id = R.string.sign_in))
                }
            }
        }

        Column(modifier = Modifier.weight(0.5f)) {
            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    lifecycleScope.launch {
                        isLoading = true
                        try {
                            createUserUseCase.execute(
                                LoginParam(
                                    email = email,
                                    password = password
                                )
                            )
                            onLogin()
                        } finally {
                            isLoading = false
                        }
                    }
                },
                enabled = !isLoading,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                } else {
                    Text(text = stringResource(id = R.string.create_account))
                }
            }
        }
    }
}