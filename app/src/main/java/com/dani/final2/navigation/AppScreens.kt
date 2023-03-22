package com.dani.final2.navigation

sealed class AppScreens(val route: String) {
    object LoginScreen : AppScreens("LoginScreen")
}