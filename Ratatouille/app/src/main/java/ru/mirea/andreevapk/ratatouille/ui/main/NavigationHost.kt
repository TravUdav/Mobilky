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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.mirea.andreevapk.ratatouille.ui.FavMealScreen
import ru.mirea.andreevapk.ratatouille.ui.RecommendationsScreen
import ru.mirea.andreevapk.ratatouille.ui.UploadImageToDetectScreen
import ru.mirea.andreevapk.ratatouille.ui.auth.AuthActivity
import ru.mirea.andreevapk.ratatouille.ui.meal.MealListScreen

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
        composable(Screen.MealListScreen.route) {
            MealListScreen(getMealListUseCase = mainViewModel.getMealListUseCase)
        }
        composable(Screen.FavDishScreen.route) {
            FavMealScreen(
                getFavMealListUseCase = mainViewModel.getFavMealListUseCase,
                addFavMealUseCase = mainViewModel.addFavMealUseCase,
                removeFavMealByIdUseCase = mainViewModel.removeFavMealByIdUseCase
            )
        }
        composable(Screen.RecommendationsScreen.route) {
            RecommendationsScreen(getRecommendMealListUseCase = mainViewModel.getRecommendMealListUseCase)
        }
        composable(Screen.UploadImageToDetectScreen.route) {
            UploadImageToDetectScreen(uploadMealToDetectUseCase = mainViewModel.uploadMealToDetectUseCase)
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