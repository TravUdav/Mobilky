package ru.mirea.andreevapk.ratatouille.ui.main

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.mirea.andreevapk.ratatouille.ui.auth.AuthActivity
import ru.mirea.andreevapk.ratatouille.ui.fave.FavMealScreen
import ru.mirea.andreevapk.ratatouille.ui.fave.FavMealViewModel
import ru.mirea.andreevapk.ratatouille.ui.fave.FavMealViewModelFactory
import ru.mirea.andreevapk.ratatouille.ui.meal.MealListScreen
import ru.mirea.andreevapk.ratatouille.ui.meal.MealListViewModel
import ru.mirea.andreevapk.ratatouille.ui.meal.MealListViewModelFactory
import ru.mirea.andreevapk.ratatouille.ui.recommendations.RecommendationsScreen
import ru.mirea.andreevapk.ratatouille.ui.recommendations.RecommendationsViewModel
import ru.mirea.andreevapk.ratatouille.ui.recommendations.RecommendationsViewModelFactory
import ru.mirea.andreevapk.ratatouille.ui.upload.UploadImageToDetectScreen
import ru.mirea.andreevapk.ratatouille.ui.upload.UploadImageViewModel
import ru.mirea.andreevapk.ratatouille.ui.upload.UploadImageViewModelFactory

//Используем compose навигацию https://developer.android.com/develop/ui/compose/navigation
@Composable
fun NavigationHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MealListScreen.route,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        composable(route = Screen.MealListScreen.route) {
            val mealListViewModel = ViewModelProvider(
                LocalContext.current as ViewModelStoreOwner,
                MealListViewModelFactory(mainViewModel.getMealListUseCase)
            ).get(MealListViewModel::class.java)
            MealListScreen(viewModel = mealListViewModel)
        }

        composable(route = Screen.FavMealScreen.route) {
            val favDishViewModel = ViewModelProvider(
                LocalContext.current as ViewModelStoreOwner,
                FavMealViewModelFactory(
                    mainViewModel.getFavMealListUseCase,
                    mainViewModel.addFavMealUseCase,
                    mainViewModel.removeFavMealByIdUseCase
                )
            ).get(FavMealViewModel::class.java)
            FavMealScreen(viewModel = favDishViewModel)
        }

        composable(route = Screen.RecommendationsScreen.route) {
            val recommendationsViewModel = ViewModelProvider(
                LocalContext.current as ViewModelStoreOwner,
                RecommendationsViewModelFactory(mainViewModel.getRecommendMealListUseCase)
            ).get(RecommendationsViewModel::class.java)
            RecommendationsScreen(viewModel = recommendationsViewModel)
        }

        composable(route = Screen.UploadImageToDetectScreen.route) {
            val uploadImageViewModel = ViewModelProvider(
                LocalContext.current as ViewModelStoreOwner,
                UploadImageViewModelFactory(mainViewModel.uploadMealToDetectUseCase)
            ).get(UploadImageViewModel::class.java)
            UploadImageToDetectScreen(viewModel = uploadImageViewModel)
        }
        composable(Screen.UserProfileScreen.route) {
            UserProfileScreen()
        }
    }
}

@Composable
fun UserProfileScreen() {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val intent = Intent(context, AuthActivity::class.java)
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }
}