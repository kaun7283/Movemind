package com.movemind.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ── MoveMind Brand Colors ──────────────────────────────────────────────────
val OrangeVibrant  = Color(0xFFFF6B00)
val OrangeLight    = Color(0xFFFF8C33)
val OrangeDim      = Color(0xFFBF5000)

val NavyDark       = Color(0xFF121A26)
val NavyMid        = Color(0xFF1C2839)
val SlateMid       = Color(0xFF334155)
val SlateLight     = Color(0xFF4A5568)

val SurfaceCard    = Color(0xFF1E2D42)
val SurfaceInput   = Color(0xFF243347)

val TextPrimary    = Color(0xFFFFFFFF)
val TextSecondary  = Color(0xFFB0BECA)
val TextHint       = Color(0xFF6B7C93)

val SuccessGreen   = Color(0xFF22C55E)
val ErrorRed       = Color(0xFFEF4444)

// ── Dark Color Scheme ──────────────────────────────────────────────────────
private val MoveMindDarkScheme = darkColorScheme(
    primary          = OrangeVibrant,
    onPrimary        = TextPrimary,
    primaryContainer = OrangeDim,
    secondary        = SlateLight,
    onSecondary      = TextPrimary,
    background       = NavyDark,
    onBackground     = TextPrimary,
    surface          = NavyMid,
    onSurface        = TextPrimary,
    surfaceVariant   = SurfaceCard,
    onSurfaceVariant = TextSecondary,
    error            = ErrorRed,
    onError          = TextPrimary,
    outline          = SlateLight,
)

@Composable
fun MoveMindTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MoveMindDarkScheme,
        typography  = MoveMindTypography,
        content     = content
    )
}
