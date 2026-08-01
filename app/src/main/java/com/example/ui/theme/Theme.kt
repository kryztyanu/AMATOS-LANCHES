package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
  primary = RedPrimary,
  onPrimary = Color.White,
  primaryContainer = RedPrimaryContainer,
  onPrimaryContainer = Color.White,
  secondary = YellowDeliveryBanner,
  onSecondary = YellowBannerText,
  background = SurfaceBackground,
  onBackground = TextDark,
  surface = SurfaceContainer,
  onSurface = TextDark,
  surfaceVariant = Color(0xFFF6F3F2),
  onSurfaceVariant = TextSecondary,
  outline = BorderOutline,
  outlineVariant = Color(0xFFE5E2E1)
)

private val DarkColorScheme = darkColorScheme(
  primary = RedPrimary,
  onPrimary = Color.White,
  primaryContainer = RedPrimaryContainer,
  onPrimaryContainer = Color.White,
  secondary = YellowDeliveryBanner,
  onSecondary = YellowBannerText,
  background = Color(0xFF1C1B1B),
  onBackground = Color(0xFFFCF9F8),
  surface = Color(0xFF2B2929),
  onSurface = Color(0xFFFCF9F8),
  surfaceVariant = Color(0xFF383535),
  onSurfaceVariant = Color(0xFFDCD9D9),
  outline = Color(0xFF5D3F3E)
)

@Composable
fun AmatosLanchesTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}

