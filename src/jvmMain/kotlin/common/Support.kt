package common

import androidx.compose.ui.graphics.Color

fun String.toColor(): Color {
    val colorInt = this.replace("#", "").toLong(16)
    val a = (colorInt shr 24 and 0xff) / 255.0f
    val r = (colorInt shr 16 and 0xff) / 255.0f
    val g = (colorInt shr 8 and 0xff) / 255.0f
    val b = (colorInt and 0xff) / 255.0f
    return Color(r, g, b)
}
