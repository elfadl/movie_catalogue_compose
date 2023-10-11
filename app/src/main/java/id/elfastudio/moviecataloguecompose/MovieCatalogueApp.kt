package id.elfastudio.moviecataloguecompose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.elfastudio.moviecataloguecompose.navigation.NavigationItem
import id.elfastudio.moviecataloguecompose.navigation.Screen
import id.elfastudio.moviecataloguecompose.ui.pages.detail_movie.DetailScreen
import id.elfastudio.moviecataloguecompose.ui.pages.favorite.FavoriteScreen
import id.elfastudio.moviecataloguecompose.ui.pages.movie.MovieScreen
import id.elfastudio.moviecataloguecompose.ui.pages.profile.ProfileScreen
import id.elfastudio.moviecataloguecompose.ui.pages.search.SearchScreen
import id.elfastudio.moviecataloguecompose.ui.theme.MovieCatalogueComposeTheme

@Composable
fun MovieCatalogueApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var actionBarTitle by remember { mutableStateOf("Movie") }

    Scaffold(
        topBar = {
            actionBarTitle = stringResource(
                id = when (currentRoute) {
                    Screen.Movie.route -> R.string.movie
                    Screen.Profile.route -> R.string.profile
                    Screen.Favorite.route -> R.string.favorite
                    Screen.DetailMovie.route -> R.string.detail_movie
                    else -> R.string.app_name
                }
            )
            if (currentRoute != Screen.Search.route) {
                TopAppBar(
                    modifier = modifier,
                    title = { Text(text = actionBarTitle) },
                    navigationIcon = if (currentRoute == Screen.DetailMovie.route) {
                        {
                            IconButton(
                                onClick = { navController.navigateUp() },
                                modifier = Modifier.testTag("back_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    } else null,
                    actions = {
                        if (currentRoute == Screen.Movie.route) {
                            IconButton(
                                onClick = {
                                    navController.navigate(Screen.Search.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                modifier = Modifier
                                    .semantics(mergeDescendants = true){
                                        contentDescription = "search"
                                    }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (currentRoute != Screen.DetailMovie.route && currentRoute != Screen.Search.route) {
                BottomBar(navController = navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Movie.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Movie.route) {
                MovieScreen(
                    navigateToDetail = { movieId ->
                        navController.navigate(Screen.DetailMovie.createRoute(movieId))
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(Screen.Search.route) {
                SearchScreen(navigateToDetail = { movieId ->
                    navController.navigate(Screen.DetailMovie.createRoute(movieId))
                })
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(navigateToDetail = { movieId ->
                    navController.navigate(Screen.DetailMovie.createRoute(movieId))
                })
            }
            composable(
                route = Screen.DetailMovie.route,
                arguments = listOf(navArgument("movieId") {
                    type = NavType.IntType
                })
            ) {
                val id = it.arguments?.getInt("movieId") ?: 0
                DetailScreen(
                    movieId = id
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItem = listOf(
            NavigationItem(
                title = stringResource(R.string.movie),
                icon = Icons.Default.Movie,
                screen = Screen.Movie
            ),
            NavigationItem(
                title = stringResource(R.string.favorite),
                icon = Icons.Default.Favorite,
                screen = Screen.Favorite
            ),
            NavigationItem(
                title = stringResource(R.string.profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            )
        )
        BottomNavigation {
            navigationItem.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null
                        )
                    },
                    label = { Text(text = item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                        .semantics(mergeDescendants = true) {
                            contentDescription = "${item.screen.route}_page"
                        }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieCataloguePreview() {
    MovieCatalogueComposeTheme {
        MovieCatalogueApp()
    }
}