
package common.component
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import styles.CairoTypography

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextButton(
    width:Dp=50.dp,
    textSize: TextUnit =15.sp,
    text: String ="",
    colors: List<Color>,
    hoverColor: Color = Color.White,
    onClick: () -> Unit,
    cornerRadius: Dp = 30.dp,
) {
    val gradient = Brush.horizontalGradient(colors)
    var isHovered by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (isHovered) 1.075f else 1f)
    val color by animateColorAsState(if (isHovered) hoverColor else Color.Transparent)
    Spacer(modifier = Modifier.size(0.dp, 15.dp))

    Box(
        modifier = Modifier
            .width(width)
            .pointerMoveFilter(
                onEnter = { isHovered = true; true },
                onExit = { isHovered = false; true }
            )
            .padding(4.dp)
            .scale(scale)
            .background(gradient, RoundedCornerShape(cornerRadius))
            .clip(RoundedCornerShape(cornerRadius))
            .shadow(
                elevation = if (isHovered) 8.dp else 4.dp,
                shape = RoundedCornerShape(cornerRadius),
                clip = true
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
                indication = null
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = text, color = Color.White, style = CairoTypography.h4, fontWeight = FontWeight.SemiBold, fontSize = textSize)
        }
    }
    Spacer(modifier = Modifier.size(5.dp, 0.dp))
}