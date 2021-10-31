package com.shubhamkumarwinner.composenoteapp.feature_note.presentation.notes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shubhamkumarwinner.composenoteapp.core.util.TestTags
import com.shubhamkumarwinner.composenoteapp.di.AppModule
import com.shubhamkumarwinner.composenoteapp.feature_note.presentation.MainActivity
import com.shubhamkumarwinner.composenoteapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.shubhamkumarwinner.composenoteapp.feature_note.presentation.util.Screen
import com.shubhamkumarwinner.composenoteapp.ui.theme.ComposeNoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
@ExperimentalAnimationApi
class NotesEndToEndTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp(){
        hiltRule.inject()
        composeRule.setContent {
            ComposeNoteAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ){
                    composable(route = Screen.NotesScreen.route){
                        NotesScreen(navController = navController)
                    }
                    composable(
                        route = Screen.AddEditNoteScreen.route
                                + "?noteId={noteId}&noteColor={noteColor}",
                        arguments = listOf(
                            navArgument(name = "noteId"){
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(name = "noteColor"){
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ){
                        val color = it.arguments?.getInt("noteColor") ?: -1
                        AddEditNoteScreen(navController = navController, noteColor = color)
                    }
                }
            }
        }
    }

    @Test
    fun saveNewNote_editAfterwards(){
        // Click on FAB to get to add note screen
        composeRule.onNodeWithContentDescription("Add").performClick()

        // Enter texts in title and content text fields
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("test-title")
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).performTextInput("test-content")

        // Save the new
        composeRule.onNodeWithContentDescription("Save").performClick()

        // Make sure there is a note in the list with our title end content
        composeRule.onNodeWithText("test-title").assertIsDisplayed()
        composeRule.onNodeWithText("test-title").performClick()

        // Make sure title and content text fields contain note title and content
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).assertTextEquals("test-title")
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).assertTextEquals("test-content")

        // Add the text "2" to the title text field
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("2")

        // Update the note
        composeRule.onNodeWithContentDescription("Save").performClick()

        // Make sure the update was applied to the list
        composeRule.onNodeWithText("test-title").assertIsDisplayed()
    }
}