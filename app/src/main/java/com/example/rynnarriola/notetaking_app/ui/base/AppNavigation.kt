package com.example.rynnarriola.notetaking_app.ui.base

import AddNoteScreen
import PendingTaskScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rynnarriola.notetaking_app.ui.screen.EditNoteScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.PendingTaskScreen.route) {
        composable(route = Screen.PendingTaskScreen.route) {
            PendingTaskScreen(navController = navController)
        }
        composable(route = Screen.AddNoteScreen.route) {
            AddNoteScreen(navController = navController)
        }
        composable("${Screen.EditNoteScreen.route}/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull() ?: return@composable
            EditNoteScreen(navController = navController, noteId = noteId)
        }

    }
}