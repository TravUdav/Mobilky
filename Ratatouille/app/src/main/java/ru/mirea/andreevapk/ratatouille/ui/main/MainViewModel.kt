package ru.mirea.andreevapk.ratatouille.ui.main

import androidx.lifecycle.ViewModel
import ru.mirea.andreevapk.data.mock.MockDishRepositoryImpl
import ru.mirea.andreevapk.data.mock.MockFavDishRepositoryImpl
import ru.mirea.andreevapk.domain.usecase.AddFavDishUseCase
import ru.mirea.andreevapk.domain.usecase.GetDishListUseCase
import ru.mirea.andreevapk.domain.usecase.GetFavDishListUseCase
import ru.mirea.andreevapk.domain.usecase.GetRecommendDishListUseCase
import ru.mirea.andreevapk.domain.usecase.RemoveFavDishByIdUseCase
import ru.mirea.andreevapk.domain.usecase.UploadDishToDetectUseCase

class MainViewModel : ViewModel() {

    private val favDishRepository = MockFavDishRepositoryImpl()
    private val dishRepository = MockDishRepositoryImpl()

    val getDishListUseCase = GetDishListUseCase(dishRepository)
    val getFavDishListUseCase = GetFavDishListUseCase(favDishRepository)
    val addFavDishUseCase = AddFavDishUseCase(favDishRepository)
    val removeFavDishByIdUseCase = RemoveFavDishByIdUseCase(favDishRepository)
    val getRecommendDishListUseCase = GetRecommendDishListUseCase(dishRepository)
    val uploadDishToDetectUseCase = UploadDishToDetectUseCase(dishRepository)
}