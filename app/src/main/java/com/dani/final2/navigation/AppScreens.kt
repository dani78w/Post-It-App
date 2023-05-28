package com.dani.final2.navigation

sealed class AppScreens(val route: String) {
    object LoginScreen : AppScreens("LoginScreen")
    object PremiumScreen : AppScreens("PremiumScreen")
    object HomeScreen : AppScreens("HomeScreen")
    object CreateAcountScreen : AppScreens("CreateAcountScreen")
    object ListasScreen : AppScreens("Listas")
    object SessionMScreen : AppScreens("Sesion")
    object MapasScreen : AppScreens("MapasScreen")
    object SqlScreen : AppScreens("SqlScreen")
    object ListEditScreen: AppScreens("ListEditScreen")
    object qrScannerScreen: AppScreens("qrScannerScreen")
}