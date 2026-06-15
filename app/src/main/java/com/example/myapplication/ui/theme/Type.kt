package com.example.myapplication.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Typographie-Einstellungen für Material Design 3.
 * Definiert die Schriftstile für verschiedene UI-Komponenten.
 * Hier wird nur bodyLarge überschrieben, andere Stile verwenden die Standardwerte.
 */
val Typography = Typography(
    // Standardgröße für längere Texte im Body-Bereich (z.B. Beschreibungen)
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Weitere Text-Stile sind verfügbar und könnten hier überschrieben werden:
    - titleLarge: Große Titel
    - labelSmall: Kleine Beschriftungen
    - und weitere mehr...
    
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)