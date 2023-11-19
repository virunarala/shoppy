package dev.virunarala.shoppy.navigation

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home")
    object CartScreen: Screen("cart")
    object FavouritesScreen: Screen("favourites")

    /**
     * @param args All the arguments to pass to a route
     * @return The Route with all the arguments appended */
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}