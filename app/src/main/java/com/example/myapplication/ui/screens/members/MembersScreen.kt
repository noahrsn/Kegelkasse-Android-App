package com.example.myapplication.ui.screens.members

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.models.Member
import com.example.myapplication.viewmodels.KegelViewModel
import java.text.NumberFormat
import java.util.Locale

/**
 * Mitglieder-Übersichtsbildschirm.
 * Zeigt alle Vereinsmitglieder mit ihren aktuellen Schulden in einer Liste an.
 * Die Schulden sind farbcodiert:
 * - Grün: Keine Schulden
 * - Orange: Kleine Schulden (bis 5 €)
 * - Rot: Große Schulden (über 5 €)
 *
 * @param viewModel KegelViewModel mit Mitgliederdaten
 */
@Composable
fun MembersScreen(viewModel: KegelViewModel) {
    // Lädt die aktuelle Mitgliederliste als State
    val members by viewModel.members.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Header mit Titel
        Surface(tonalElevation = 2.dp) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Mitglieder",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Scrollbare Liste aller Mitglieder
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(members, key = { it.id }) { member ->
                MemberCard(member = member)
            }
        }
    }
}

/**
 * Einzelne Mitglieds-Karte für die Mitglieder-Liste.
 * Zeigt:
 * - Profilbuchstabe in farbigem Kreis
 * - Name des Mitglieds
 * - Aktuelle Schulden mit Farb-Codierung
 *
 * @param member Das darzustellende Mitglied
 */
@Composable
private fun MemberCard(member: Member) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Profilkreis mit Anfangsbuchstabe
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(48.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = member.name.first().toString(),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Mitglieds-Name
            Text(
                text = member.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )

            // Schulden mit Farbcodierung
            // Rot: > 5€, Orange: > 0€, Grün: keine Schulden
            val debtColor = when {
                member.debt > 5.0 -> Color(0xFFB71C1C)      // Dunkelrot für große Schulden
                member.debt > 0.0 -> Color(0xFFE65100)      // Orange für kleine Schulden
                else -> Color(0xFF2E7D32)                   // Grün für keine Schulden
            }

            // Schulden-Anzeige mit farbigem Hintergrund
            Surface(
                shape = MaterialTheme.shapes.small,
                color = debtColor.copy(alpha = 0.12f)
            ) {
                Text(
                    text = formatCurrency(member.debt),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = debtColor,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }
}

// Hilfsfunktion: Konvertiert einen Double-Wert in deutsches Währungsformat (z.B. "5,50 €")
private fun formatCurrency(amount: Double): String =
    NumberFormat.getCurrencyInstance(Locale.GERMANY).format(amount)
