package ru.mirea.andreevapk.ratatouille

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ru.mirea.andreevapk.data.mock.MockDishRepositoryImpl
import ru.mirea.andreevapk.data.mock.MockFavDishRepositoryImpl
import ru.mirea.andreevapk.data.mock.MockFirebaseAuth
import ru.mirea.andreevapk.data.repository.UserRepositoryImpl
import ru.mirea.andreevapk.domain.usecase.AddFavDishUseCase
import ru.mirea.andreevapk.domain.usecase.GetDishListUseCase
import ru.mirea.andreevapk.domain.usecase.GetFavDishListUseCase
import ru.mirea.andreevapk.domain.usecase.GetRecommendDishListUseCase
import ru.mirea.andreevapk.domain.usecase.GetUserUseCase
import ru.mirea.andreevapk.domain.usecase.LoginByEmailUseCase
import ru.mirea.andreevapk.domain.usecase.LogoutUserUseCase
import ru.mirea.andreevapk.domain.usecase.RemoveFavDishByIdUseCase
import ru.mirea.andreevapk.domain.usecase.SetUserNameUseCase
import ru.mirea.andreevapk.domain.usecase.UploadDishToDetectUseCase
import ru.mirea.andreevapk.ratatouille.ui.DishListScreen
import ru.mirea.andreevapk.ratatouille.ui.FavDishScreen
import ru.mirea.andreevapk.ratatouille.ui.RecommendationsScreen
import ru.mirea.andreevapk.ratatouille.ui.UploadImageToDetectScreen
import ru.mirea.andreevapk.ratatouille.ui.UserProfileScreen
import ru.mirea.andreevapk.ratatouille.ui.theme.RatatouilleTheme

val mockAuth = MockFirebaseAuth()

val userRepository = UserRepositoryImpl(mockAuth)
val favDishRepository = MockFavDishRepositoryImpl()
val dishRepository = MockDishRepositoryImpl()

val getDishListUseCase = GetDishListUseCase(dishRepository)
val getUserUseCase = GetUserUseCase(userRepository)
val setUserNameUseCase = SetUserNameUseCase(userRepository)
val logoutUserUseCase = LogoutUserUseCase(userRepository)
val loginByEmailUseCase = LoginByEmailUseCase(userRepository)
val getFavDishListUseCase = GetFavDishListUseCase(favDishRepository)
val addFavDishUseCase = AddFavDishUseCase(favDishRepository)
val removeFavDishByIdUseCase = RemoveFavDishByIdUseCase(favDishRepository)
val getRecommendDishListUseCase = GetRecommendDishListUseCase(dishRepository)
val uploadDishToDetectUseCase = UploadDishToDetectUseCase(dishRepository)

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RatatouilleTheme {
                // Setup Navigation
                val navController = rememberNavController()

                // Create a state for controlling the drawer
                val drawerState = rememberDrawerState(DrawerValue.Closed)

                // Get a coroutine scope
                val coroutineScope = rememberCoroutineScope()

                // ModalNavigationDrawer for Material3
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        // Pass drawerState to the DrawerContent so that we can close it on item click
                        DrawerContent(navController, drawerState)
                    },
                    content = {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = { Text("Your App") },
                                    navigationIcon = {
                                        // Add the hamburger icon to open the drawer
                                        IconButton(onClick = {
                                            // Toggle the drawer state when the hamburger icon is clicked
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
                                    startDestination = Screen.DishListScreen.route, // DishListScreen is the root screen
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(paddingValues)
                                ) {
                                    composable(Screen.DishListScreen.route) {
                                        DishListScreen(getDishListUseCase = getDishListUseCase)
                                    }
                                    composable(Screen.UserProfileScreen.route) {
                                        UserProfileScreen(
                                            getUserUseCase = getUserUseCase,
                                            setUserNameUseCase = setUserNameUseCase,
                                            logoutUserUseCase = logoutUserUseCase,
                                            loginByEmailUseCase = loginByEmailUseCase
                                        )
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

    // Close the drawer when navigation occurs
    LaunchedEffect(navigateTo) {
        navigateTo?.let {
            // Perform the navigation
            navController.navigate(it)
            // Close the drawer after navigation
            drawerState.close()
            // Reset the navigation state
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
