package styles

import androidx.compose.ui.graphics.Color
import utils.toColor

object AppColors {
    val primary: Color = "#3B5EA1".toColor()
    val secondary:Color = "#9747FF".toColor()
    val background:Color = "#FFFFFF".toColor()
    val hintText:Color = "#B8B8B8".toColor()
    val white:Color = "#ffffff".toColor()
    val blue:Color = "#3B5EA1".toColor()
    val green:Color = "#3F6F52".toColor()
    val Red:Color = "#FA1717".toColor()
    val Red1:Color = "#A04134".toColor()
    val Red2:Color = "#FD0C0C".toColor()


    val blueGradient = listOf(blue.copy(alpha = 0.36f), blue.copy(alpha = 0.84f),blue)
    val RedGradient = listOf(Red2.copy(alpha = 0.36f), Red1.copy(alpha = 0.87f), Red)
    val GreenGradient = listOf(green.copy(alpha = 0.36f), green.copy(alpha = 0.84f), green)
    val hoverBlueGradient = listOf( white.copy(alpha = 0.1f), white.copy(alpha = 0.1f))


}
