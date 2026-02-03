package com.techinnovation.learning.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = SageGreen,
    onPrimary = WarmInk,
    primaryContainer = BlueMist,
    onPrimaryContainer = WarmInk,
    secondary = BeigeSoft,
    onSecondary = WarmInk,
    secondaryContainer = SageGreen,
    onSecondaryContainer = WarmInk,
    tertiary = RoseTint,
    onTertiary = WarmInk,
    tertiaryContainer = RoseTint,
    onTertiaryContainer = WarmInk,
    background = BeigeSoft,
    onBackground = WarmInk,
    surface = Color(0xFFFDFBF7),
    onSurface = WarmInk,
    surfaceVariant = Color(0xFFF3F0EA),
    onSurfaceVariant = WarmInk,
    outlineVariant = MistOutline,
    errorContainer = Color(0xFFFDECEC),
    onErrorContainer = CalmSlate
)

@Composable
fun RishtonKeMasailTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
