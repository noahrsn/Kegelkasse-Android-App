package com.example.myapplication.ui.screens.session

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.models.Member
import com.example.myapplication.viewmodels.KegelViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

/**
 * Kegelabend-Bildschirm: Zeigt alle Vereinsmitglieder in einer Liste.
 * Beim Antippen eines Mitglieds öffnet sich ein BottomSheet mit verfügbaren Strafen.
 * Nach Strafenauswahl wird eine Bestätigung als Snackbar angezeigt.
 *
 * @param viewModel KegelViewModel mit Mitglieder- und Strafendaten
 * @param snackbarHostState State für die Anzeige von Bestätigungsmeldungen
 */
@Composable
fun SessionScreen(
    viewModel: KegelViewModel,
    snackbarHostState: SnackbarHostState
) {
    // Lädt die aktuelle Mitgliederliste als State
    val members by viewModel.members.collectAsState()
    // Verwaltet das zuletzt ausgewählte Mitglied (für das BottomSheet)
    var selectedMember by remember { mutableStateOf<Member?>(null) }
    // Coroutine-Scope für Snackbar-Anzeige
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        // Header mit Titel
        Surface(tonalElevation = 2.dp) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Kegelabend",
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
                // Zeige eine Karte für jedes Mitglied, die bei Tap aktiviert wird
                MemberSessionCard(member = member, onClick = { selectedMember = member })
            }
        }
    }

    // BottomSheet mit Strafen-Auswahl (wird angezeigt, wenn ein Mitglied selected ist)
    selectedMember?.let { member ->
        PenaltyBottomSheet(
            member = member,
            penalties = viewModel.penalties,
            onPenaltySelected = { penalty ->
                // Füge Strafe hinzu und schließe BottomSheet
                viewModel.addPenalty(member.id, penalty)
                selectedMember = null
                // Zeige Bestätigung als Snackbar
                scope.launch {
                    snackbarHostState.showSnackbar(
                        "Strafe hinzugefügt: ${penalty.name} (${formatCurrency(penalty.amount)})"
                    )
                }
            },
            onDismiss = { selectedMember = null }
        )
    }
}

/**
 * Einzelne Mitglieds-Karte für den Kegelabend.
 * Zeigt:
 * - Profilbuchstabe in farbigem Kreis
 * - Name und Aktionstext
 * - Aktuelle Schulden
 *
 * @param member Das darzustellende Mitglied
 * @param onClick Callback beim Antippen der Karte
 */
@Composable
private fun MemberSessionCard(member: Member, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
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
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(44.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = member.name.first().toString(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            // Name und Aktionsbeschriftung
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = member.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Tippen zum Strafen",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            // Schulden-Anzeige (rot wenn > 0)
            Text(
                text = formatCurrency(member.debt),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (member.debt > 0) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.primary
            )
        }
    }
}

// Hilfsfunktion: Konvertiert einen Double-Wert in deutsches Währungsformat (z.B. "5,50 €")
private fun formatCurrency(amount: Double): String =
    NumberFormat.getCurrencyInstance(Locale.GERMANY).format(amount)
