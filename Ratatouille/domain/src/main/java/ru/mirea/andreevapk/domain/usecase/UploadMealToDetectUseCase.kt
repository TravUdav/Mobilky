package ru.mirea.andreevapk.domain.usecase

import android.graphics.Bitmap
import ru.mirea.andreevapk.domain.model.Meal
import ru.mirea.andreevapk.domain.repository.MealRepository

class UploadMealToDetectUseCase(private val repository: MealRepository) {
    suspend fun execute(image: Bitmap): Meal {
        return repository.uploadMealToDetect(image)
    }
}