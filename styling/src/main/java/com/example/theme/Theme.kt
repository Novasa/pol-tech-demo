package com.example.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import com.google.android.material.composethemeadapter.createMdcTheme


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
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val context = LocalContext.current
    val layoutDirection = LocalLayoutDirection.current
    val (color, typography, shapes) = createMdcTheme(
        context = context,
        layoutDirection = layoutDirection
    )

    if(color != null && typography != null && shapes != null){
        MaterialTheme(
            colors = color,
            typography = typography,
            shapes = shapes,
            content = content
        )
    } else {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}