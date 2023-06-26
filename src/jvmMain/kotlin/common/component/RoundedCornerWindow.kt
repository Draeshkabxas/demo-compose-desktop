package common.component

import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*

@Composable
fun RoundedCornerWindow(
    size: DpSize,
    onClose: () -> Unit,
    icon: String = "images/logo.svg",
    content: @Composable() (FrameWindowScope.(MutableState<DpSize>) -> Unit),
) {
    val windowSize= mutableStateOf(size)
    Window(
        icon = painterResource(icon),
        onCloseRequest = { onClose() },
        state = WindowState(
            placement = WindowPlacement.Floating,
            position = WindowPosition(Alignment.Center),
            size = windowSize.value
        ),
        transparent = true,
        undecorated = true, //transparent window must be undecorated
    ) {
        WindowDraggableArea {
            content(windowSize)
        }
    }
}