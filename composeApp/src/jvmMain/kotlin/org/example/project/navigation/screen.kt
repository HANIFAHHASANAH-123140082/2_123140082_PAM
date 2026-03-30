package org.example.project.navigation

sealed interface Screen {
    object Home : Screen
    object Detail : Screen
    object NoteList : Screen
    data class NoteDetail(val noteId: Int) : Screen
    object Notes : Screen
    object Favorites : Screen
    object Profile : Screen
}

data class BottomNavItem(
    val route: Screen,
    val label: String,
    val icon: String
)

val bottomNavItems = listOf(
    BottomNavItem(Screen.Notes, "Notes", "📝"),
    BottomNavItem(Screen.Favorites, "Favorites", "❤️"),
    BottomNavItem(Screen.Profile, "Profile", "👤")
)