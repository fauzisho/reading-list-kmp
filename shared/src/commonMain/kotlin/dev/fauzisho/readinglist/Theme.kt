package dev.fauzisho.readinglist

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

/** Android 12+ derives the colours from the wallpaper; every other platform uses the default scheme. */
@Composable
expect fun appColorScheme(): ColorScheme
