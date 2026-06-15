package com.example.myapplication.data.models

/**
 * Datenklasse für eine Strafenart.
 * Definiert die verschiedenen Strafen, die im Kegelspiel gegeben werden können.
 *
 * @param id Eindeutige Kennung der Strafe (z.B. "p1")
 * @param name Beschreibung (z.B. "Pudel")
 * @param icon Emoji-Icon zur visuellen Darstellung (z.B. "🎳")
 * @param amount Strafenbetrag in Euro (z.B. 0.10)
 */
data class PenaltyItem(
    val id: String,
    val name: String,
    val icon: String,
    val amount: Double
)
