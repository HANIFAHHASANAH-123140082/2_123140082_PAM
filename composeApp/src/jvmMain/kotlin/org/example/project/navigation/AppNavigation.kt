package org.example.project.navigation

import androidx.compose.runtime.*
import org.example.project.screens.DetailScreen
import org.example.project.screens.HomeScreen

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

    when (currentScreen) {
        is Screen.Home -> {
            HomeScreen(
                onNavigateToDetail = {
                    currentScreen = Screen.Detail
                }
            )
        }
        is Screen.Detail -> {
            DetailScreen(
                onBack = {
                    currentScreen = Screen.Home
                }
            )
        }
    }
}