package ru.mirea.andreevapk.ratatouille.ui.recommendations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mirea.andreevapk.domain.usecase.GetRecommendMealListUseCase

class RecommendationsViewModelFactory(
    private val getRecommendMealListUseCase: GetRecommendMealListUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecommendationsViewModel::class.java)) {
            return RecommendationsViewModel(getRecommendMealListUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
