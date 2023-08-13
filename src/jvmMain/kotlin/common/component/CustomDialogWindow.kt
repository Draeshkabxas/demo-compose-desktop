import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import java.awt.Frame
import javax.swing.SwingUtilities
import kotlin.system.exitProcess

@Composable
fun CustomDialogWindow(
    title: String,
    state: WindowState = WindowState(size = DpSize(700.dp, 800.dp)),
    onDismiss: () -> Unit,
    background: Color = Color.White,
    icon: String = "images/logo.svg",
    buttons: @Composable () -> Unit = {},
    errorMessage: @Composable () -> Unit = {},
    showErrorMessage: Boolean = false,
    content: @Composable () -> Unit
) {
    val state by remember { mutableStateOf(state) }
    Window(
        title = title,
        onCloseRequest = {
            onDismiss()
        },
        state = rememberWindowState(placement = state.placement, position = state.position, size = state.size),
        icon = painterResource(icon),
        undecorated = false,
        resizable = false,
        alwaysOnTop = true // Set the window to be always on top
    ) {
        WindowDraggableArea {
            Surface(
                modifier = Modifier
                    .size(1000.dp)
                    .padding(5.dp)
                    .shadow(3.dp, RoundedCornerShape(20.dp)),
                color = background,
                shape = RoundedCornerShape(20.dp), //window has round corners now
                elevation = 40.dp
            ) {
                Box {
                    Box(
                        modifier = Modifier.align(Alignment.TopEnd),
                    ) {
                        IconButton(onClick = { state.isMinimized = true }) {
                            Icon(
                                painter = painterResource("images/minimize.svg"),
                                contentDescription = "Minimize Application",
                                tint = Color.Gray.copy(alpha = 0.4f),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        val isWindowInFullScreen = state.placement == WindowPlacement.Fullscreen
                        IconButton(
                            onClick = {
                                if (isWindowInFullScreen)
                                    state.placement = WindowPlacement.Floating
                                else
                                    state.placement = WindowPlacement.Fullscreen
                            }

                        ) {
                            val iconPath = if (isWindowInFullScreen) "floating.svg" else "maximize.svg"
                            Icon(
                                painter = painterResource("images/$iconPath"),
                                contentDescription = "Full Screen Application",
                                tint = Color.Gray.copy(alpha = 0.6f),
                                modifier = Modifier.size(22.dp)
                            )
                        }
//                        IconButton(onClick = {
//                            //onDismiss()
//                            closeWindow()
//                        }) {
//                            Image(
//                                painter = painterResource("images/exit.svg"),
//                                contentDescription = "Exit Application",
//                                modifier = Modifier.size(22.dp)
//                            )
//                        }
                    }
                    Box(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    ) {
                        Box(modifier = Modifier.align(Alignment.TopCenter).fillMaxWidth()) {
                            Text(text = title, modifier = Modifier.align(Alignment.TopCenter))
                        }
                        Box(
                            modifier = Modifier.padding(all = 40.dp).fillMaxWidth(),
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            content()
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        Box(
                            modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth(),
                        ) {
                            Column(horizontalAlignment = Alignment.Start) { // Align buttons and error message to the right
                                buttons()
                                if (showErrorMessage)
                                    errorMessage()
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun closeWindow() {
    SwingUtilities.invokeLater {
        val frame = Frame.getFrames().firstOrNull { it.isActive }
        frame?.dispose()
    }
}