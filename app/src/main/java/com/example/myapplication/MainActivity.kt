package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.navigation.Screen
import com.example.myapplication.ui.navigation.bottomNavItems
import com.example.myapplication.ui.screens.dashboard.DashboardScreen
import com.example.myapplication.ui.screens.members.MembersScreen
import com.example.myapplication.ui.screens.session.SessionScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodels.KegelViewModel

/**
 * Hauptaktivität der Kegelkasse-App.
 * Verwaltet:
 * - Den Navigation-Stack zwischen Bildschirmen
 * - Den globalen ViewModel mit App-Daten
 * - Die Bottom Navigation Bar
 * - Snackbar-Benachrichtigungen
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Aktiviere Edge-to-Edge-Modus (nutzt vollständig den Screen)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                // Navigation Controller für das Wechseln zwischen Screens
                val navController = rememberNavController()
                // Globaler ViewModel für alle Bildschirme
                val viewModel: KegelViewModel = viewModel()
                // State für Snackbar-Nachrichten
                val snackbarHostState = remember { SnackbarHostState() }
                // Aktuelle Navigations-Route (um aktives Tab zu markieren)
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // Hauptlayout mit Bottom Navigation Bar und Snackbar
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    bottomBar = {
                        NavigationBar {
                            // Erstelle ein Navigation-Item für jeden definier Screen
                            bottomNavItems.forEach { screen ->
                                NavigationBarItem(
                                    icon = { Icon(screen.icon, contentDescription = screen.label) },
                                    label = { Text(screen.label) },
                                    selected = currentRoute == screen.route,
                                    onClick = {
                                        // Navigation mit Stack-Management (verhindert Duplikate)
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    // Navigation Graph: Definiert die einzelnen Screens und deren Routes
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Dashboard.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // Dashboard-Screen (Startbildschirm)
                        composable(Screen.Dashboard.route) {
                            DashboardScreen(
                                viewModel = viewModel,
                                onStartSession = {
                                    // Navigation zur Session mit launchSingleTop (verhindert Duplikate)
                                    navController.navigate(Screen.Session.route) {
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }
                        // Session-Screen (Kegelabend mit Bestrafungen)
                        composable(Screen.Session.route) {
                            SessionScreen(
                                viewModel = viewModel,
                                snackbarHostState = snackbarHostState
                            )
                        }
                        // Members-Screen (Mitglieder-Übersicht)
                        composable(Screen.Members.route) {
                            MembersScreen(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}
