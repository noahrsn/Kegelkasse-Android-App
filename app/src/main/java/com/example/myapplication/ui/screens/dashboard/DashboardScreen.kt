package com.example.myapplication.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.viewmodels.KegelViewModel
import java.text.NumberFormat
import java.util.Locale

/**
 * Hauptbildschirm (Dashboard) der Kegelkasse-App.
 * Zeigt einen Überblick mit:
 * - Begrüßung des aktuellen Benutzers
 * - KPI-Karten: Schulden, nächster Termin, Kassenstand
 * - Button zum Starten eines neuen Kegelabends
 *
 * @param viewModel KegelViewModel mit Daten für Mitglieder und App-Status
 * @param onStartSession Callback zum Navigieren zur Session
 */
@Composable
fun DashboardScreen(
    viewModel: KegelViewModel,
    onStartSession: () -> Unit
) {
    // Lädt die aktuelle Mitgliederliste als State
    val members by viewModel.members.collectAsState()
    // Findet den aktuellen Benutzer und ermittelt dessen Schulden (Standard: 0.0)
    val userDebt = members.find { it.id == viewModel.currentUserId }?.debt ?: 0.0

    // Vertikale Liste mit Scroll-Funktionalität
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(Modifier.height(8.dp))

        // Personalisierte Begrüßung mit Welle-Emoji
        Text(
            text = "Hallo, ${viewModel.currentUserName} 👋",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        // Untertitel der App
        Text(
            text = "Kegelkasse – Dein Vereinsbudget",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(4.dp))

        // KPI-Karte 1: Zeigt Schulden des Benutzers an (orange wenn > 0, grün sonst)
        KpiCard(
            title = "Meine Schulden",
            value = formatCurrency(userDebt),
            icon = Icons.Default.AccountBalanceWallet,
            containerColor = if (userDebt > 0) MaterialTheme.colorScheme.errorContainer
                             else MaterialTheme.colorScheme.primaryContainer,
            contentColor = if (userDebt > 0) MaterialTheme.colorScheme.onErrorContainer
                           else MaterialTheme.colorScheme.onPrimaryContainer
        )
        // KPI-Karte 2: Zeigt den nächsten Kegelabend an
        KpiCard(
            title = "Nächster Termin",
            value = viewModel.nextSession,
            icon = Icons.Default.CalendarToday,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
        // KPI-Karte 3: Zeigt den Kassenstand des Vereins an
        KpiCard(
            title = "Kassenstand",
            value = formatCurrency(viewModel.clubBalance),
            icon = Icons.Default.Savings,
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        )

        Spacer(Modifier.height(8.dp))

        // Button zum Starten eines neuen Kegelabends
        Button(
            onClick = onStartSession,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Icon(Icons.Default.PlayArrow, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Neuen Kegelabend starten", style = MaterialTheme.typography.titleMedium)
        }
    }
}

/**
 * Wiederverwendbare KPI-Karte mit Icon, Titel und Wert.
 * Zeigt Informationen in farbigen Karten an.
 *
 * @param title Titel der Karte (z.B. "Meine Schulden")
 * @param value Angezeigter Wert (z.B. "5,50 €")
 * @param icon Material-Icon zur visuellen Darstellung
 * @param containerColor Hintergrundfarbe der Karte
 * @param contentColor Textfarbe der Karte
 */
@Composable
private fun KpiCard(
    title: String,
    value: String,
    icon: ImageVector,
    containerColor: Color,
    contentColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Icon auf der linken Seite
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(36.dp))
            // Titel und Wert in einer Spalte
            Column {
                Text(text = title, style = MaterialTheme.typography.labelLarge)
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// Konvertiert einen Double-Wert in ein deutsches Währungsformat (z.B. "5,50 €")
private fun formatCurrency(amount: Double): String =
    NumberFormat.getCurrencyInstance(Locale.GERMANY).format(amount)
