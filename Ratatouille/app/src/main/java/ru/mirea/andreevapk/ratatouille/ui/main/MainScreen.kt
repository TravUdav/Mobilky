package ru.mirea.andreevapk.ratatouille.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ru.mirea.andreevapk.ratatouille.R

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .fillMaxHeight()
                    .background(Color.LightGray)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.carrot),
                        contentDescription = "Top Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        contentScale = ContentScale.Crop
                    )
                    DrawerContent(navController = navController) {
                        coroutineScope.launch { drawerState.close() }
                    }
                }
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
            navController.navigate(Screen.MealListScreen.route)
            onCloseDrawer()
        }
        DrawerButton(label = "Favorite Dishes") {
            navController.navigate(Screen.FavMealScreen.route)
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
        Text(
            text = label,
            color = Color.Black
        )
    }
}

sealed class Screen(val route: String) {
    object MealListScreen : Screen("meal_list")
    object UserProfileScreen : Screen("user_profile")
    object FavMealScreen : Screen("fav_meal")
    object RecommendationsScreen : Screen("recommendations")
    object UploadImageToDetectScreen : Screen("upload_image_to_detect")
}