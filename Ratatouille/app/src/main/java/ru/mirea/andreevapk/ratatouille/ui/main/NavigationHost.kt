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
import ru.mirea.andreevapk.ratatouille.ui.DishListScreen
import ru.mirea.andreevapk.ratatouille.ui.FavDishScreen
import ru.mirea.andreevapk.ratatouille.ui.RecommendationsScreen
import ru.mirea.andreevapk.ratatouille.ui.UploadImageToDetectScreen
import ru.mirea.andreevapk.ratatouille.ui.auth.AuthActivity

@Composable
fun NavigationHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.DishListScreen.route,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        composable(Screen.DishListScreen.route) {
            DishListScreen(getDishListUseCase = mainViewModel.getDishListUseCase)
        }
        composable(Screen.FavDishScreen.route) {
            FavDishScreen(
                getFavDishListUseCase = mainViewModel.getFavDishListUseCase,
                addFavDishUseCase = mainViewModel.addFavDishUseCase,
                removeFavDishByIdUseCase = mainViewModel.removeFavDishByIdUseCase
            )
        }
        composable(Screen.RecommendationsScreen.route) {
            RecommendationsScreen(getRecommendDishListUseCase = mainViewModel.getRecommendDishListUseCase)
        }
        composable(Screen.UploadImageToDetectScreen.route) {
            UploadImageToDetectScreen(uploadDishToDetectUseCase = mainViewModel.uploadDishToDetectUseCase)
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