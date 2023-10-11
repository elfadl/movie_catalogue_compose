package id.elfastudio.moviecataloguecompose

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import id.elfastudio.moviecataloguecompose.navigation.Screen
import id.elfastudio.moviecataloguecompose.ui.theme.MovieCatalogueComposeTheme
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieCatalogueAppTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MovieCatalogueComposeTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                MovieCatalogueApp(navController = navController)
            }
        }
    }

    @Test
    fun make_sure_movie_list_is_loaded() {
        composeTestRule.onNodeWithContentDescription("movie_page").performClick()
        composeTestRule.waitUntilDoesNotExist(hasText("Loading Data..."))
        composeTestRule.onNodeWithTag("movie_0").assertIsDisplayed()
    }

    @Test
    fun make_sure_movie_detail_is_loaded() {
        composeTestRule.onNodeWithContentDescription("movie_page").performClick()
        composeTestRule.waitUntilDoesNotExist(hasText("Loading Data..."))
        composeTestRule.onNodeWithTag("movie_0").assertIsDisplayed()
        composeTestRule.onNodeWithTag("movie_0").performClick()
        composeTestRule.waitUntilDoesNotExist(hasText("Loading Data..."))
        composeTestRule.onNodeWithTag("poster").assertIsDisplayed()
        composeTestRule.onNodeWithTag("title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("overview").assertIsDisplayed()
    }

    @Test
    fun make_sure_action_favorite_is_working() {
        composeTestRule.onNodeWithContentDescription("movie_page").performClick()
        composeTestRule.waitUntilDoesNotExist(hasText("Loading Data..."))
        composeTestRule.onNodeWithTag("movie_0").assertIsDisplayed()
        composeTestRule.onNodeWithTag("movie_0").performClick()
        composeTestRule.waitUntilDoesNotExist(hasText("Loading Data..."))
        composeTestRule.onNodeWithTag("poster").assertIsDisplayed()
        composeTestRule.onNodeWithTag("title").assertIsDisplayed()
        val movieTitle =
            composeTestRule.onNodeWithTag("title")
                .getTextValue()
                .replace("[", "")
                .replace("]", "")
        composeTestRule.onNodeWithTag("overview").assertIsDisplayed()
        composeTestRule.onNodeWithTag("overview").assertIsDisplayed()
        composeTestRule.onNode(
            withRole(Role.Checkbox)
                .and(isNotSelected())
                .and(hasText("Add to Favorite"))
        ).assertIsDisplayed()
        composeTestRule.onNode(
            withRole(Role.Checkbox)
                .and(isNotSelected())
                .and(hasText("Add to Favorite"))
        ).performClick()
        composeTestRule.onNode(
            withRole(Role.Checkbox)
                .and(isSelected())
                .and(hasText("Added to Favorite"))
        ).assertIsDisplayed()
        composeTestRule.onNodeWithTag("back_button").performClick()
        composeTestRule.onNodeWithContentDescription("favorite_page")
        composeTestRule.onNodeWithText(movieTitle).assertIsDisplayed()
        composeTestRule.onNodeWithText(movieTitle).performClick()
        composeTestRule.onNode(
            withRole(Role.Checkbox)
                .and(isSelected())
                .and(hasText("Added to Favorite"))
        ).assertIsDisplayed()
        composeTestRule.onNode(
            withRole(Role.Checkbox)
                .and(isSelected())
                .and(hasText("Added to Favorite"))
        ).performClick()
        composeTestRule.onNode(
            withRole(Role.Checkbox)
                .and(isNotSelected())
                .and(hasText("Add to Favorite"))
        ).assertIsDisplayed()
    }

    @Test
    fun make_sure_profile_page_can_display_correctly() {
        composeTestRule.onNodeWithContentDescription("about_page").performClick()
        composeTestRule.onNodeWithStringId(R.string.my_name).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.my_email).assertIsDisplayed()
    }

    @Test
    fun make_sure_search_can_display_correctly() {
        composeTestRule.onNodeWithContentDescription("movie_page").performClick()
        composeTestRule.waitUntilDoesNotExist(hasText("Loading Data..."))
        val movieTitle =
            composeTestRule.onAllNodes(hasContentDescription("movie_title"))[0]
                .getTextValue()
                .replace("[", "")
                .replace("]", "")
        composeTestRule.onNodeWithContentDescription("search").performClick()
        composeTestRule.onNodeWithContentDescription("search_bar").performTextInput(movieTitle)
        composeTestRule.onNodeWithText(movieTitle)
    }

    @Test
    fun navhost_verify_start_destination() {
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(Screen.Movie.route, currentRoute)
    }

}