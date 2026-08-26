package plat.lab5.pinto_ramirez.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFff7654),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF6204da),
    secondaryContainer = Color(0xFFe1f3f9),
    tertiary = Color(0xFF21a3d2),
    background = Color(0xFFf5f5f5),
    primaryContainer = Color(0xFFfdfdfd)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFF54927),
    onPrimary = Color(0xFF000000),
    secondary = Color(0xFF5FA9C2),
    secondaryContainer = Color(0xFF8A51A6),
    tertiary = Color(0xFF0083AB),
    background = Color(0xFF464C52),
    primaryContainer = Color(0xFF919AA3)
    /*
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun Lab5Theme(
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}