package ru.mirea.andreevapk.ratatouille.ui.upload

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadImageToDetectScreen(viewModel: UploadImageViewModel) {

    val uploadStatusMessage by viewModel.uploadStatusMessage

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Upload Meal") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = uploadStatusMessage)

            Button(onClick = {
                val imageUri = "some_image_uri"
//                viewModel.uploadMealImage(imageUri)
            }) {
                Text("Select Image")
            }
        }
    }
}