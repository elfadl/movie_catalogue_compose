package id.elfastudio.moviecataloguecompose.navigation

sealed class Screen(val route: String){
    object Movie: Screen("movie")
    object Profile: Screen("about")
    object Search: Screen("search")
    object Favorite: Screen("favorite")
    object DetailMovie: Screen("movie/{movieId}"){
        fun createRoute(movieId: Int) = "movie/$movieId"
    }
}
