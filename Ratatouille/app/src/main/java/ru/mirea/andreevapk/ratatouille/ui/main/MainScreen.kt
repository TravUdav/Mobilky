package ru.mirea.andreevapk.ratatouille.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController = navController) {
                coroutineScope.launch { drawerState.close() }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    MainTopBar(onMenuClick = { coroutineScope.launch { drawerState.open() } })
                },
                content = { paddingValues ->
                    NavigationHost(
                        navController = navController,
                        paddingValues = paddingValues,
                        mainViewModel = mainViewModel
                    )
                }
            )
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(onMenuClick: () -> Unit) {
    TopAppBar(
        title = { Text("Ratatouille") },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Open Drawer")
            }
        }
    )
}

@Composable
fun DrawerContent(navController: NavHostController, onCloseDrawer: () -> Unit) {
    Column {
        DrawerButton(label = "Dish List") {
            navController.navigate(Screen.DishListScreen.route)
            onCloseDrawer()
        }
        DrawerButton(label = "Favorite Dishes") {
            navController.navigate(Screen.FavDishScreen.route)
            onCloseDrawer()
        }
        DrawerButton(label = "Recommendations") {
            navController.navigate(Screen.RecommendationsScreen.route)
            onCloseDrawer()
        }
        DrawerButton(label = "Upload Image") {
            navController.navigate(Screen.UploadImageToDetectScreen.route)
            onCloseDrawer()
        }
        DrawerButton(label = "User Profile") {
            navController.navigate(Screen.UserProfileScreen.route)
            onCloseDrawer()
        }
    }
}

@Composable
fun DrawerButton(label: String, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(label)
    }
}

sealed class Screen(val route: String) {
    object DishListScreen : Screen("dish_list")
    object UserProfileScreen : Screen("user_profile")
    object FavDishScreen : Screen("fav_dish")
    object RecommendationsScreen : Screen("recommendations")
    object UploadImageToDetectScreen : Screen("upload_image_to_detect")
}