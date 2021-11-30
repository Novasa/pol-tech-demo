package com.example.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


private val DarkColorPalette = darkColors(
    primary = PolitiBlueDark,
    background = PolitiBlack,
    onBackground = PolitiWhite,
    onPrimary = PolitiWhite
)

private val LightColorPalette = lightColors(
    primary = PolitiBlueLight,
    background = PolitiWhite,
    onBackground = PolitiBlueDark,
    onPrimary = PolitiBlueDark
)

@Composable
fun FactsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}