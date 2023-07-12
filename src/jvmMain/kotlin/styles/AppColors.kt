package styles

import androidx.compose.ui.graphics.Color
import common.toColor

object AppColors {
    val primary: Color = "#3B5EA1".toColor()
    val secondary:Color = "#9747FF".toColor()
    val background:Color = "#FFFFFF".toColor()
    val hintText:Color = "#B8B8B8".toColor()
    val white:Color = "#ffffff".toColor()
    val blue:Color = "#3B5EA1".toColor()
    val green:Color = "#3F6F52".toColor()

    val blueGradient = listOf(blue, blue.copy(alpha = 0.84f),blue.copy(alpha = 0.36f))
}
