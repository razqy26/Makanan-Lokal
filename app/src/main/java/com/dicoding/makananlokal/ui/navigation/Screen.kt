package com.dicoding.makananlokal.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object DetailFood : Screen("home/{foodId}") {
        fun createRoute(foodId: Long) = "home/$foodId"
    }
}