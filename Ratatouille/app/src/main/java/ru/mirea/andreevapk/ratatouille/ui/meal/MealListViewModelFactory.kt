package ru.mirea.andreevapk.ratatouille.ui.meal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mirea.andreevapk.domain.usecase.GetMealListUseCase

class MealListViewModelFactory(
    private val getMealListUseCase: GetMealListUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealListViewModel::class.java)) {
            return MealListViewModel(getMealListUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}