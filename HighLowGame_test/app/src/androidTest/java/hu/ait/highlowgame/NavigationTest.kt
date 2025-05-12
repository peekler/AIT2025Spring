package hu.ait.highlowgame

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import hu.ait.highlowgame.ui.theme.HighLowGameTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun startGameTest() {
        // Given
        // Start the app
        composeTestRule.setContent {
            HighLowGameTheme {
                MainNavigation()
            }
        }

        // When
        composeTestRule.onNodeWithText("Start").performClick()

        // Then
        composeTestRule.onNodeWithText(text = "Guess", substring = true).assertIsDisplayed()
    }

    @Test
    fun showAboutTest() {
        // Given
        // Start the app
        composeTestRule.setContent {
            HighLowGameTheme {
                MainNavigation()
            }
        }

        // When
        composeTestRule.onNodeWithText("About").performClick()

        // Then
        composeTestRule.onNodeWithText(
            text = "Click start to use the game, good luck!").assertIsDisplayed()
    }

    @Test
    fun guessCountTest() {
        // Given
        // Start the app
        composeTestRule.setContent {
            HighLowGameTheme  {
                MainNavigation()
            }
        }

        // When
        composeTestRule.onNodeWithText("Start").performClick()

        // Then
        composeTestRule.onNodeWithText(text = "Guess (0)").assertIsDisplayed()
        composeTestRule.onNodeWithText(text = "Guess", substring = true).performClick()
        composeTestRule.onNodeWithText(text = "Guess", substring = true).performClick()
        composeTestRule.onNodeWithText(text = "Guess", substring = true).performClick()

        composeTestRule.onNodeWithText(text = "Guess (3)").assertIsDisplayed()
    }
}