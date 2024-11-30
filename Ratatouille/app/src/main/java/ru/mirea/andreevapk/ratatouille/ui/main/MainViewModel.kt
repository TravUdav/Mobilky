package ru.mirea.andreevapk.ratatouille.ui.main

import androidx.lifecycle.ViewModel
import ru.mirea.andreevapk.data.api.TheMealDbClient
import ru.mirea.andreevapk.data.mock.MockFavMealRepositoryImpl
import ru.mirea.andreevapk.data.repository.MealRepositoryImpl
import ru.mirea.andreevapk.domain.usecase.AddFavMealUseCase
import ru.mirea.andreevapk.domain.usecase.GetFavMealListUseCase
import ru.mirea.andreevapk.domain.usecase.GetMealListUseCase
import ru.mirea.andreevapk.domain.usecase.GetRecommendMealListUseCase
import ru.mirea.andreevapk.domain.usecase.RemoveFavMealByIdUseCase
import ru.mirea.andreevapk.domain.usecase.UploadMealToDetectUseCase

class MainViewModel : ViewModel() {

    private val theMealDbApi = TheMealDbClient.api
    private val mealRepository = MealRepositoryImpl(theMealDbApi)

    private val favMealRepository = MockFavMealRepositoryImpl()

    val getMealListUseCase = GetMealListUseCase(mealRepository)
    val getFavMealListUseCase = GetFavMealListUseCase(favMealRepository)
    val addFavMealUseCase = AddFavMealUseCase(favMealRepository)
    val removeFavMealByIdUseCase = RemoveFavMealByIdUseCase(favMealRepository)
    val getRecommendMealListUseCase = GetRecommendMealListUseCase(mealRepository)
    val uploadMealToDetectUseCase = UploadMealToDetectUseCase(mealRepository)
}