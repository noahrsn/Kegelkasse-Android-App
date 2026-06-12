package com.example.myapplication.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Dashboard : Screen("dashboard", "Dashboard", Icons.Default.Home)
    object Session : Screen("session", "Kegelabend", Icons.Default.EmojiEvents)
    object Members : Screen("members", "Mitglieder", Icons.Default.People)
}

val bottomNavItems = listOf(Screen.Dashboard, Screen.Session, Screen.Members)
