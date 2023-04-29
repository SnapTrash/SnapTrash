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
    //primary = Purple80,
    //secondary = PurpleGrey80,
    //tertiary = Pink80
    primary = Green2 ,
    onPrimary = WhiteCream,
    primaryContainer = GreenMain1,
    onPrimaryContainer = WhiteCream,
    secondary = WhiteCream ,
    tertiary = Gray1 ,
    background = Black1 ,
    surfaceVariant = Gray1,
    error = Red2


)

private val LightColorScheme = lightColorScheme(
    //primary = Purple40,
    //secondary = PurpleGrey40,
    //tertiary = Pink40
    primary = GreenMain1,
    onPrimary = GreenMain1 ,
    primaryContainer = GreenMain1,
    onPrimaryContainer = WhiteCream,
    secondary = Black1,
    tertiary = Green3,
    background = WhiteCream,
    surfaceVariant = Color.White,
    error = Red1


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
            (view.context as Activity).window.statusBarColor = colorScheme.primaryContainer.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )


}