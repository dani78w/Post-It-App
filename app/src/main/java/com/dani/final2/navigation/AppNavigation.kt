package com.dani.final2.navigation



import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dani.final2.screens.LoginScreen

@Composable
fun AppNavigation() {

    var navController = rememberNavController()
    var currentSong = 0
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route) {
        composable(AppScreens.LoginScreen.route) {
            LoginScreen(navController)
        }

    }
}