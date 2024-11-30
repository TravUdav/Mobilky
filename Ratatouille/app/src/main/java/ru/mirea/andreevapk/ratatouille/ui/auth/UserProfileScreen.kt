package ru.mirea.andreevapk.ratatouille.ui.auth

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
import androidx.compose.runtime.livedata.observeAsState
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
import ru.mirea.andreevapk.domain.model.GUEST_ID
import ru.mirea.andreevapk.domain.model.GUEST_NAME
import ru.mirea.andreevapk.domain.model.User
import ru.mirea.andreevapk.ratatouille.R

@Composable
fun UserProfileScreen(authViewModel: AuthViewModel) {
    val user by authViewModel.userLiveData.observeAsState(initial = User(GUEST_ID, GUEST_NAME))
    val isSignedIn = user.id != GUEST_ID
    var userName by remember { mutableStateOf(user.name) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(user) {
        userName = user.name
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
                value = userName ?: "",
                onValueChange = { userName = it },
                label = { Text("User Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    authViewModel.updateUserName(userName ?: "")
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
                    onClick = { authViewModel.logout() },
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(text = stringResource(id = R.string.sign_out))
                }
            } else {
                SignInSection(
                    email = email,
                    onEmailChange = { email = it },
                    password = password,
                    onPasswordChange = { password = it },
                    onLogin = {
                        isLoading = true
                        authViewModel.login(email, password) {
                            email = ""
                            password = ""
                            isLoading = false
                        }
                    },
                    onCreateAccount = {
                        isLoading = true
                        authViewModel.createUser(email, password) {
                            email = ""
                            password = ""
                            isLoading = false
                        }
                    },
                    isLoading = isLoading
                )
            }
        }
    }
}

@Composable
fun SignInSection(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    onCreateAccount: () -> Unit,
    isLoading: Boolean
) {
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
                onClick = { onLogin() },
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
                onClick = { onCreateAccount() },
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