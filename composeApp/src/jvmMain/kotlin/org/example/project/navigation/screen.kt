package org.example.project.navigation

sealed interface Screen {
    object Home : Screen
    object Detail : Screen
}