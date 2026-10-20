package dev.fauzisho.readinglist

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun appColorScheme(): ColorScheme {
    val dark = isSystemInDarkTheme()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {   // dynamic colour exists from Android 12 (API 31)
        val context = LocalContext.current
        return if (dark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }
    return if (dark) darkColorScheme() else lightColorScheme()
}
