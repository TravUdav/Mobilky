package ru.mirea.andreevapk.ratatouille.ui.upload

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mirea.andreevapk.domain.usecase.UploadMealToDetectUseCase

class UploadImageViewModel(
    private val uploadMealToDetectUseCase: UploadMealToDetectUseCase
) : ViewModel() {

    private val _uploadStatusMessage = mutableStateOf("")
    val uploadStatusMessage: State<String> = _uploadStatusMessage

    fun uploadMealImage(imageUri: Bitmap) {
        viewModelScope.launch {
            val result = uploadMealToDetectUseCase.execute(imageUri)

            _uploadStatusMessage.value = result.toString()
        }
    }

    init {
        _uploadStatusMessage.value = "Ready to upload an image."
    }
}
