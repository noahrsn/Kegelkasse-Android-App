package com.example.myapplication.data

import com.example.myapplication.data.models.Member
import com.example.myapplication.data.models.PenaltyItem

/**
 * Mock-Daten für die App.
 * Enthält Beispiel-Vereinsmitglieder und Strafenkatalog.
 * In einer echten App würde dies aus einer Datenbank oder API kommen.
 */
object MockData {
    // Liste aller Vereinsmitglieder mit ID, Name und aktuellen Schulden
    val members = listOf(
        Member("1", "Noah Roosen", 1.50),        // 1,50 € Schulden
        Member("2", "Max Mustermann", 0.00),     // Keine Schulden
        Member("3", "Anna Schmidt", 4.20),       // 4,20 € Schulden
        Member("4", "Peter Müller", 2.70),       // 2,70 € Schulden
        Member("5", "Lisa Weber", 0.00),         // Keine Schulden
        Member("6", "Klaus Fischer", 8.60)       // 8,60 € Schulden
    )

    // Strafenkatalog mit ID, Name, Emoji-Icon und Betrag
    val penalties = listOf(
        PenaltyItem("p1", "Pudel", "🎳", 0.10),          // Kegeln zu nah an der Bande
        PenaltyItem("p2", "Klingel", "🔔", 0.50),       // Regelverstoß
        PenaltyItem("p3", "Verspätung", "⏰", 2.00),     // Zu spät zum Termin
        PenaltyItem("p4", "Rinnenwurf", "🎯", 0.50),    // Kegelwurf in falscher Bahn
        PenaltyItem("p5", "Handy", "📱", 1.00),         // Handy während Spiel
        PenaltyItem("p6", "Regelverstoß", "📋", 3.00)    // Großer Regelverstoß
    )
}
