package common.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import styles.CairoTypography

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GradientButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    colors: List<Color>,
    modifier: Modifier = Modifier,
    cornerRadius: Dp,

    ) {
    val gradient = Brush.verticalGradient(
        colors = colors
    )
    val hoverScale = remember { Animatable(1f) }
    val isHovered = remember { mutableStateOf(false) }

    val scale = derivedStateOf {
        if (isHovered.value) 1.1f else 1f
    }

    LaunchedEffect(isHovered.value) {
        if (isHovered.value) {
            hoverScale.animateTo(
                targetValue = 1.1f,
                animationSpec = tween(durationMillis = 10000)
            )
        } else {
            hoverScale.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 10000)
            )
        }
    }
    Box(
        modifier = modifier
                .clickable(onClick = onClick)
                .padding(6.dp)
            .clip(RoundedCornerShape(cornerRadius))
                .scale(scale.value)
            .shadow(
                spotColor = Color.Black,
                elevation = 8.dp,
                shape = RoundedCornerShape(cornerRadius)
            )
            .onPointerEvent(PointerEventType.Move) {
            }
            .onPointerEvent(PointerEventType.Enter) {
                isHovered.value = true
            }
            .onPointerEvent(PointerEventType.Exit) {
                isHovered.value = false
            }


    ) {
        Canvas(
            modifier = Modifier.matchParentSize()
        ) {
            drawRect(brush = gradient)
        }

        Row(        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),

                verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(end = 8.dp)
            )

            Text(
                text = text,
                color = Color.White,
                style = CairoTypography.h3,
            )
        }
    }
}