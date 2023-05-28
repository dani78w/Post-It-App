package com.dani.final2.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dani.final2.screens.*

@Composable
fun AppNavigation() {

    var navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.ListasScreen.route ) {
        composable(AppScreens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(AppScreens.HomeScreen.route) {
            HomeScren(navController)
        }
        composable(AppScreens.CreateAcountScreen.route) {
            CreateAcountScreen(navController)
        }
        composable(AppScreens.SessionMScreen.route) {
            SessionMScreen(navController)
        }
        composable(AppScreens.ListasScreen.route) {
            ListasScreen(navController)
        }
        composable(AppScreens.MapasScreen.route) {
            MapasScreen(navController)
        }
        composable(AppScreens.SqlScreen.route) {
            SqlScreen(navController)
        }
        composable(AppScreens.ListEditScreen.route) {
            ListEditScreen(navController)
        }

    }
}