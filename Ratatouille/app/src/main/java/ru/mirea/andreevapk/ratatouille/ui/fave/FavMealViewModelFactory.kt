package ru.mirea.andreevapk.ratatouille.ui.fave

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mirea.andreevapk.domain.usecase.AddFavMealUseCase
import ru.mirea.andreevapk.domain.usecase.GetFavMealListUseCase
import ru.mirea.andreevapk.domain.usecase.RemoveFavMealByIdUseCase

class FavMealViewModelFactory(
    private val getFavMealListUseCase: GetFavMealListUseCase,
    private val addFavMealUseCase: AddFavMealUseCase,
    private val removeFavMealByIdUseCase: RemoveFavMealByIdUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavMealViewModel::class.java)) {
            return FavMealViewModel(
                getFavMealListUseCase,
                addFavMealUseCase,
                removeFavMealByIdUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}