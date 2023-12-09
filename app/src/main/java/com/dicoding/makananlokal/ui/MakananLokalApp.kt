package com.dicoding.makananlokal.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.makananlokal.R
import com.dicoding.makananlokal.ui.navigation.NavigationItem
import com.dicoding.makananlokal.ui.navigation.Screen
import com.dicoding.makananlokal.ui.screen.detail.DetailScreen
import com.dicoding.makananlokal.ui.screen.favorite.FavoriteScreen
import com.dicoding.makananlokal.ui.screen.home.HomeScreen
import com.dicoding.makananlokal.ui.screen.profile.ProfileScreen

@Composable
fun MakananLokalApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailFood.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { foodId ->
                        navController.navigate(Screen.DetailFood.createRoute(foodId))
                    }
                )
            }
            composable(Screen.Favorite.route) {
                val context = LocalContext.current
                FavoriteScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = Screen.DetailFood.route,
                arguments = listOf(navArgument("foodId") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("foodId") ?: -1L
                DetailScreen(
                    foodId = id,
                    navigateToFavorite = {
                    }
                )
            }
            composable(
                route = Screen.DetailFood.route,
                arguments = listOf(navArgument("foodId") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("foodId") ?: -1L
                DetailScreen(
                    foodId = id,
                    navigateToFavorite = {
                        navController.popBackStack()
                        navController.navigate(Screen.Favorite.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,

        ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_favorite),
                icon = Icons.Default.Favorite,
                screen = Screen.Favorite
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),

            )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}