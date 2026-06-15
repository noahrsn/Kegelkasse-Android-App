package com.example.myapplication.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Sealed Class für die Navigation zwischen Bildschirmen.
 * Jeder Screen hat einen eindeutigen Route (für Navigation), Label (für UI) und Icon.
 *
 * @param route Eindeutige Navigation-Route (wird in NavHost verwendet)
 * @param label Anzeigename im Navigation-Bar
 * @param icon Material-Icon für den Navigation-Bar
 */
sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    // Dashboar-Bildschirm: Startseite mit KPIs
    object Dashboard : Screen("dashboard", "Dashboard", Icons.Default.Home)
    // Session-Bildschirm: Liste der Mitglieder zum Bestrafen
    object Session : Screen("session", "Kegelabend", Icons.Default.EmojiEvents)
    // Members-Bildschirm: Übersicht aller Vereinsmitglieder
    object Members : Screen("members", "Mitglieder", Icons.Default.People)
}

// Liste aller Bildschirme, die mit Navigation-Bar erreichbar sind
val bottomNavItems = listOf(Screen.Dashboard, Screen.Session, Screen.Members)
