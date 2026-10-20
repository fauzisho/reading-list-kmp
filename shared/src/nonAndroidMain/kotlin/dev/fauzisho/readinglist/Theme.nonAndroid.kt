package dev.fauzisho.readinglist

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/** One actual for iOS, desktop and the browser. */
@Composable
actual fun appColorScheme(): ColorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
