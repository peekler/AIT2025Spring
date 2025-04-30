package hu.bme.aut.myplacescomposefirebase.ui.navigation

sealed class MainNavigation(val route: String) {
    object LoginScreen : MainNavigation("loginscreen")
    object MainScreen : MainNavigation("mainscreen")
}
