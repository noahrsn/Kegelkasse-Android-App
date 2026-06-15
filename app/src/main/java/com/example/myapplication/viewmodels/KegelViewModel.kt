package com.example.myapplication.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.MockData
import com.example.myapplication.data.models.Member
import com.example.myapplication.data.models.PenaltyItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel für die Kegelkasse-App.
 * Verwaltet:
 * - Mitgliederdaten und deren Schulden
 * - Strafenkatalog
 * - App-Informationen (nächster Termin, Kassenstand, aktueller Benutzer)
 * - Geschäftslogik zum Hinzufügen von Strafen
 */
class KegelViewModel : ViewModel() {

    // Reaktive Liste aller Mitglieder mit ihren Schulden
    // _members ist privat (nur lesbar), die öffentliche Variante ist read-only StateFlow
    private val _members = MutableStateFlow<List<Member>>(MockData.members)
    val members: StateFlow<List<Member>> = _members.asStateFlow()

    // Alle verfügbaren Strafen mit Namen, Emoji und Betrag
    val penalties = MockData.penalties

    // Festes Datum und Ort des nächsten Kegelabends
    val nextSession = "Fr. 20. Juni 2026 · Gasthaus Zum Kegel"

    // Aktueller Gesamtbestand der Vereinskasse
    val clubBalance = 142.80

    // Name und ID des angemeldeten Benutzers
    val currentUserName = "Noah"
    val currentUserId = "1"

    /**
     * Fügt dem Mitglied eine Strafe hinzu, indem die Schulden erhöht werden.
     * Die Mitgliederliste wird immutable aktualisiert (keine direkten Änderungen).
     *
     * @param memberId ID des Mitglieds, das bestraft wird
     * @param penalty Strafendetails (Name, Betrag, Icon)
     */
    fun addPenalty(memberId: String, penalty: PenaltyItem) {
        // Durchlaufe alle Mitglieder und erhöhe die Schulden des bestimmten Mitglieds
        _members.value = _members.value.map { member ->
            if (member.id == memberId) member.copy(debt = member.debt + penalty.amount)
            else member
        }
    }
}
