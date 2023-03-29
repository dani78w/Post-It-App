package com.dani.final2.navigation

sealed class AppScreens(val route: String) {
    object LoginScreen : AppScreens("LoginScreen")
    object CreateAcountScreen : AppScreens("CreateAcountScreen")
    object ListasScreen : AppScreens("Listas")
    object MapasScreen : AppScreens("MapasScreen")
}