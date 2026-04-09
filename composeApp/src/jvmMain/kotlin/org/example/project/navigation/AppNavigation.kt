package org.example.project.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.example.project.screens.AddNoteScreen
import org.example.project.screens.DetailScreen
import org.example.project.screens.EditNoteScreen
import org.example.project.screens.FavoritesScreen
import org.example.project.screens.HomeScreen
import org.example.project.screens.NoteDetailScreen
import org.example.project.screens.NoteListScreen
import org.example.project.screens.ProfileTabScreen

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Notes) }

    Scaffold(
        bottomBar = {
            val showBottomNav = currentScreen !is Screen.AddNote &&
                    currentScreen !is Screen.EditNote &&
                    currentScreen !is Screen.NoteDetail &&
                    currentScreen !is Screen.Detail
            if (showBottomNav) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentScreen == item.route,
                            onClick = { currentScreen = item.route },
                            icon = {
                                Text(
                                    text = item.icon,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (val screen = currentScreen) {
                is Screen.Notes -> NoteListScreen(
                    onNoteClick = { noteId -> currentScreen = Screen.NoteDetail(noteId) },
                    onAddClick = { currentScreen = Screen.AddNote }
                )
                is Screen.NoteDetail -> NoteDetailScreen(
                    noteId = screen.noteId,
                    onBack = { currentScreen = Screen.Notes },
                    onEdit = { id -> currentScreen = Screen.EditNote(id) }
                )
                is Screen.AddNote -> AddNoteScreen(
                    onBack = { currentScreen = Screen.Notes }
                )
                is Screen.EditNote -> EditNoteScreen(
                    noteId = screen.noteId,
                    onBack = { currentScreen = Screen.Notes }
                )
                is Screen.Favorites -> FavoritesScreen()
                is Screen.Profile -> ProfileTabScreen()
                is Screen.Home -> HomeScreen(
                    onNavigateToDetail = { currentScreen = Screen.Detail }
                )
                is Screen.Detail -> DetailScreen(
                    onBack = { currentScreen = Screen.Home }
                )
                is Screen.NoteList -> NoteListScreen(
                    onNoteClick = { noteId -> currentScreen = Screen.NoteDetail(noteId) }
                )
            }
        }
    }
}