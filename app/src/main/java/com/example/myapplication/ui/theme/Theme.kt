package com.example.myapplication.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Farbschema für den Dark Theme
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

// Farbschema für den Light Theme
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Weitere Farben, die überschrieben werden können:
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

/**
 * Haupttheme-Composable der App.
 * Wendet Material Design 3 Design-Prinzipien auf die gesamte App an.
 * Unterstützt dynamische Farben auf Android 12+ (wenn verfügbar).
 *
 * @param darkTheme True = Dark Theme, False = Light Theme. Standard: System-Einstellung
 * @param dynamicColor True = Dynamische Farben nutzen (Android 12+). Standard: true
 * @param content Die UI-Inhalte, die mit diesem Theme angewendet werden
 */
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamische Farbe ist auf Android 12+ verfügbar
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    // Wählt das passende Farbschema nach Bedingungen
    val colorScheme = when {
        // Dynamische Farben ab Android 12 (nutzt die Wallpaper-Farben des Systems)
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        // Dark Theme (manuelle Farbdefinition)
        darkTheme -> DarkColorScheme
        // Light Theme (manuelle Farbdefinition)
        else -> LightColorScheme
    }

    // Wende das MaterialTheme mit ausgewähltem Farbschema an
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}