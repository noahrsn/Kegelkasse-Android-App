package com.example.myapplication.ui.screens.session

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.models.Member
import com.example.myapplication.data.models.PenaltyItem
import java.text.NumberFormat
import java.util.Locale

/**
 * BottomSheet für Strafenauswahl.
 * Wird angezeigt, wenn der Benutzer ein Mitglied antippen, um es zu bestrafen.
 * Zeigt verfügbare Strafen in einem 2-Spalten-Gitter an.
 *
 * @param member Das zu bestrafende Mitglied
 * @param penalties Liste aller verfügbaren Strafen
 * @param onPenaltySelected Callback bei Strafenauswahl
 * @param onDismiss Callback beim Schließ des BottomSheets
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PenaltyBottomSheet(
    member: Member,
    penalties: List<PenaltyItem>,
    onPenaltySelected: (PenaltyItem) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Titel mit Mitglieds-Name
            Text(
                text = "Strafe für ${member.name}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            // Gitter mit Strafen-Karten (2 Spalten)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp)
            ) {
                items(penalties) { penalty ->
                    PenaltyCard(penalty = penalty, onClick = { onPenaltySelected(penalty) })
                }
            }
        }
    }
}

/**
 * Einzelne Strafen-Karte für das BottomSheet-Gitter.
 * Zeigt:
 * - Emoji-Icon (z.B. 🎳 für Pudel)
 * - Strafenname (z.B. "Pudel")
 * - Betrag (z.B. "0,10 €")
 *
 * @param penalty Die darzustellende Strafe
 * @param onClick Callback bei Auswahl
 */
@Composable
private fun PenaltyCard(penalty: PenaltyItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            // Emoji-Icon der Strafe
            Text(text = penalty.icon, style = MaterialTheme.typography.headlineMedium)
            // Name der Strafe
            Text(
                text = penalty.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            // Strafenbetrag
            Text(
                text = formatCurrency(penalty.amount),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.75f)
            )
        }
    }
}

// Hilfsfunktion: Konvertiert einen Double-Wert in deutsches Währungsformat (z.B. "0,10 €")
private fun formatCurrency(amount: Double): String =
    NumberFormat.getCurrencyInstance(Locale.GERMANY).format(amount)
