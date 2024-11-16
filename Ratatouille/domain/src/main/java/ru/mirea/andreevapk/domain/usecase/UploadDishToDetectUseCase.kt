package ru.mirea.andreevapk.domain.usecase

import android.graphics.Bitmap
import ru.mirea.andreevapk.domain.model.Dish
import ru.mirea.andreevapk.domain.repository.DishRepository

class UploadDishToDetectUseCase(private val repository: DishRepository) {
    fun execute(image: Bitmap): Dish {
        return repository.uploadDishToDetect(image)
    }
}