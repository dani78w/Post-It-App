package com.dani.final2.navigation



import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dani.final2.screens.CreateAcountScreen
import com.dani.final2.screens.Listas
import com.dani.final2.screens.ListasScreen
import com.dani.final2.screens.LoginScreen

@Composable
fun AppNavigation() {

    var navController = rememberNavController()
    var currentSong = 0
    NavHost(navController = navController, startDestination = AppScreens.ListasScreen.route) {
        composable(AppScreens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(AppScreens.CreateAcountScreen.route) {
            CreateAcountScreen(navController)
        }
        composable(AppScreens.ListasScreen.route) {
            ListasScreen(navController)
        }

    }
}