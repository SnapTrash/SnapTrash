package com.snaptrash.snaptrash.view.theme

import android.app.Activity
import android.os.Build
import android.os.Parcelable.Creator
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
import androidx.core.view.ViewCompat



private val DarkColorScheme = darkColorScheme(
    primary = GreenMain1,
    onPrimary = WhiteCream,
    surface = Gray1,
    onSurface = WhiteCream,
    primaryContainer = GreenMain1,
    onPrimaryContainer = WhiteCream,
    secondaryContainer = GreenMain1,
    onSecondaryContainer = WhiteCream,
    onBackground = Green3,
    secondary = GreenMain1,
    onSecondary = WhiteCream,
    tertiary = Gray1,
    onTertiary = WhiteCream,
    background = Black1,
    surfaceVariant = Gray1,
    onSurfaceVariant = WhiteCream,
    surfaceTint = Green2,
    outline = Green3,
    error = Red2,
    scrim = WhiteCream,
    errorContainer = Red1,
    inverseSurface = WhiteCream,
    inversePrimary = Green2,
    inverseOnSurface = Gray1
)

private val LightColorScheme = lightColorScheme(
    //primary = Purple40,
    //secondary = PurpleGrey40,
    //tertiary = Pink40
    primary = GreenMain1,
    onPrimary = WhiteCream,
    primaryContainer = GreenMain1,
    onPrimaryContainer = WhiteCream,
    onBackground = GreenMain1,
    secondary = Green2,
    onSecondary = WhiteCream,
    secondaryContainer = GreenMain1,
    onSecondaryContainer = WhiteCream,
    tertiary = Green3,
    background = WhiteCream,
    surface = Color.White,
    onSurface = GreenMain1,
    outline = GreenMain1 ,
    surfaceVariant = Color.White,
    onSurfaceVariant = Black1,
    error = Red1,
    errorContainer = Red2,
    inverseSurface = Gray1,
    inverseOnSurface = WhiteCream,
    inversePrimary = Green3,
    scrim = Black1


    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun SnapTrashTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )


}