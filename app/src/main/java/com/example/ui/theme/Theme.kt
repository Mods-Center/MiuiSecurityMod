package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// MIUI HyperOS / MIUI 13 Signature Palette
val MiuiTeal = Color(0xFF00C48C)
val MiuiTealDark = Color(0xFF009668)
val MiuiCyan = Color(0xFF00B4D8)
val MiuiBlue = Color(0xFF0077B6)
val MiuiOrange = Color(0xFFFF9F1C)
val MiuiRed = Color(0xFFFF4D4D)
val MiuiDarkBg = Color(0xFF0B131F)
val MiuiDarkSurface = Color(0xFF131F33)
val MiuiDarkCard = Color(0xFF1B2B44)
val MiuiTextPrimary = Color(0xFFE2E8F0)
val MiuiTextSecondary = Color(0xFF94A3B8)

private val DarkColorScheme = darkColorScheme(
    primary = MiuiTeal,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF004D36),
    onPrimaryContainer = Color(0xFF6EEDB8),
    secondary = MiuiCyan,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF023E59),
    onSecondaryContainer = Color(0xFF90E0EF),
    tertiary = MiuiOrange,
    background = MiuiDarkBg,
    onBackground = MiuiTextPrimary,
    surface = MiuiDarkSurface,
    onSurface = MiuiTextPrimary,
    surfaceVariant = MiuiDarkCard,
    onSurfaceVariant = MiuiTextSecondary
)

private val LightColorScheme = lightColorScheme(
    primary = MiuiTeal,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFCCFBE7),
    onPrimaryContainer = Color(0xFF00402C),
    secondary = MiuiCyan,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE0F7FA),
    onSecondaryContainer = Color(0xFF004857),
    tertiary = MiuiOrange,
    background = Color(0xFFF8FAFC),
    onBackground = Color(0xFF0F172A),
    surface = Color.White,
    onSurface = Color(0xFF0F172A),
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = Color(0xFF475569)
)

@Composable
fun MiuiSecurityTheme(
    darkTheme: Boolean = true, // Default to MIUI dark theme for authentic security center look
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.background.toArgb()
                window.navigationBarColor = colorScheme.background.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
