package ru.mirea.andreevapk.ratatouille

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ru.mirea.andreevapk.data.mock.MockDishRepositoryImpl
import ru.mirea.andreevapk.data.mock.MockFavDishRepositoryImpl
import ru.mirea.andreevapk.domain.usecase.AddFavDishUseCase
import ru.mirea.andreevapk.domain.usecase.GetDishListUseCase
import ru.mirea.andreevapk.domain.usecase.GetFavDishListUseCase
import ru.mirea.andreevapk.domain.usecase.GetRecommendDishListUseCase
import ru.mirea.andreevapk.domain.usecase.RemoveFavDishByIdUseCase
import ru.mirea.andreevapk.domain.usecase.UploadDishToDetectUseCase
import ru.mirea.andreevapk.ratatouille.ui.DishListScreen
import ru.mirea.andreevapk.ratatouille.ui.FavDishScreen
import ru.mirea.andreevapk.ratatouille.ui.RecommendationsScreen
import ru.mirea.andreevapk.ratatouille.ui.UploadImageToDetectScreen
import ru.mirea.andreevapk.ratatouille.ui.theme.RatatouilleTheme


val favDishRepository = MockFavDishRepositoryImpl()
val dishRepository = MockDishRepositoryImpl()

val getDishListUseCase = GetDishListUseCase(dishRepository)
val getFavDishListUseCase = GetFavDishListUseCase(favDishRepository)
val addFavDishUseCase = AddFavDishUseCase(favDishRepository)
val removeFavDishByIdUseCase = RemoveFavDishByIdUseCase(favDishRepository)
val getRecommendDishListUseCase = GetRecommendDishListUseCase(dishRepository)
val uploadDishToDetectUseCase = UploadDishToDetectUseCase(dishRepository)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setMainContent()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun setMainContent() {
        setContent {
            RatatouilleTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val coroutineScope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        DrawerContent(navController, drawerState)
                    },
                    content = {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = { Text("Your App") },
                                    navigationIcon = {
                                        IconButton(onClick = {
                                            coroutineScope.launch {
                                                drawerState.open()
                                            }
                                        }) {
                                            Icon(
                                                imageVector = Icons.Default.Menu,
                                                contentDescription = "Open Drawer"
                                            )
                                        }
                                    }
                                )
                            },
                            content = { paddingValues ->
                                NavHost(
                                    navController = navController,
                                    startDestination = Screen.DishListScreen.route,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(paddingValues)
                                ) {
                                    composable(Screen.DishListScreen.route) {
                                        DishListScreen(getDishListUseCase = getDishListUseCase)
                                    }
                                    composable(Screen.UserProfileScreen.route) {
                                        val context = LocalContext.current
                                        LaunchedEffect(Unit) {
                                            val intent = Intent(context, AuthActivity::class.java)
                                            context.startActivity(intent)
                                            finish()
                                        }
                                    }
                                    composable(Screen.FavDishScreen.route) {
                                        FavDishScreen(
                                            getFavDishListUseCase = getFavDishListUseCase,
                                            addFavDishUseCase = addFavDishUseCase,
                                            removeFavDishByIdUseCase = removeFavDishByIdUseCase
                                        )
                                    }
                                    composable(Screen.RecommendationsScreen.route) {
                                        RecommendationsScreen(getRecommendDishListUseCase = getRecommendDishListUseCase)
                                    }
                                    composable(Screen.UploadImageToDetectScreen.route) {
                                        UploadImageToDetectScreen(uploadDishToDetectUseCase = uploadDishToDetectUseCase)
                                    }
                                }
                            }
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun DrawerContent(navController: NavHostController, drawerState: DrawerState) {
    var navigateTo by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(navigateTo) {
        navigateTo?.let {
            navController.navigate(it)
            drawerState.close()
            navigateTo = null
        }
    }
    Column {
        TextButton(onClick = {
            navigateTo = Screen.DishListScreen.route
        }) {
            Text("Dish List")
        }
        TextButton(onClick = {
            navigateTo = Screen.FavDishScreen.route
        }) {
            Text("Favorite Dishes")
        }
        TextButton(onClick = {
            navigateTo = Screen.RecommendationsScreen.route
        }) {
            Text("Recommendations")
        }
        TextButton(onClick = {
            navigateTo = Screen.UploadImageToDetectScreen.route
        }) {
            Text("Upload Image")
        }
        TextButton(onClick = {
            navigateTo = Screen.UserProfileScreen.route
        }) {
            Text("User Profile")
        }
    }
}

sealed class Screen(val route: String) {
    object DishListScreen : Screen("dish_list")
    object UserProfileScreen : Screen("user_profile")
    object FavDishScreen : Screen("fav_dish")
    object RecommendationsScreen : Screen("recommendations")
    object UploadImageToDetectScreen : Screen("upload_image_to_detect")
}
