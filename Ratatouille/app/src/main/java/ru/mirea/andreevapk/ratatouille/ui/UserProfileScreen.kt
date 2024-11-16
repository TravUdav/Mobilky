package ru.mirea.andreevapk.ratatouille.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ru.mirea.andreevapk.domain.model.User
import ru.mirea.andreevapk.domain.usecase.GetUserUseCase
import ru.mirea.andreevapk.domain.usecase.LogoutUserUseCase
import ru.mirea.andreevapk.domain.usecase.SetUserNameUseCase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    getUserUseCase: GetUserUseCase,
    setUserNameUseCase: SetUserNameUseCase,
    logoutUserUseCase: LogoutUserUseCase
) {
    var user by remember { mutableStateOf<User?>(null) }
    var nameField by remember { mutableStateOf(TextFieldValue("")) }
    var errorMessage by remember { mutableStateOf("") }

    // Fetch user when the screen is loaded
    LaunchedEffect(Unit) {
        try {
            user = getUserUseCase.execute()
            nameField = TextFieldValue(user?.name ?: "")
        } catch (e: IllegalStateException) {
            errorMessage = e.message ?: "An error occurred"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("User Profile") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Error message
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }

            // Display User Info
            user?.let {
                Text(text = "User ID: ${it.id}")
                Text(text = "Current Name: ${it.name}")
            }

            // Input Field for Name
            OutlinedTextField(
                value = nameField,
                onValueChange = { nameField = it },
                label = { Text("Enter new name") },
                modifier = Modifier.fillMaxWidth()
            )

            // Update Name Button
            Button(
                onClick = {
                    user = user?.copy(name = nameField.text)
                    errorMessage = "Name updated successfully!"
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Update Name")
            }

            // Logout Button
            Button(
                onClick = {
                    logoutUserUseCase.execute()
                    user = null
                    errorMessage = "User logged out successfully!"
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Logout")
            }
        }
    }
}