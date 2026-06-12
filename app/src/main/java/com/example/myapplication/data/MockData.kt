package com.example.myapplication.data

import com.example.myapplication.data.models.Member
import com.example.myapplication.data.models.PenaltyItem

object MockData {
    val members = listOf(
        Member("1", "Noah Roosen", 1.50),
        Member("2", "Max Mustermann", 0.00),
        Member("3", "Anna Schmidt", 4.20),
        Member("4", "Peter Müller", 2.70),
        Member("5", "Lisa Weber", 0.00),
        Member("6", "Klaus Fischer", 8.60)
    )

    val penalties = listOf(
        PenaltyItem("p1", "Pudel", "🎳", 0.10),
        PenaltyItem("p2", "Klingel", "🔔", 0.50),
        PenaltyItem("p3", "Verspätung", "⏰", 2.00),
        PenaltyItem("p4", "Rinnenwurf", "🎯", 0.50),
        PenaltyItem("p5", "Handy", "📱", 1.00),
        PenaltyItem("p6", "Regelverstoß", "📋", 3.00)
    )
}
