package ru.mirea.andreevapk.ratatouille.ui.upload

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mirea.andreevapk.domain.usecase.UploadMealToDetectUseCase

class UploadImageViewModelFactory(
    private val uploadMealToDetectUseCase: UploadMealToDetectUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UploadImageViewModel::class.java)) {
            return UploadImageViewModel(uploadMealToDetectUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
