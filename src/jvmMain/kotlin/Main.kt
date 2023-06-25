import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import di.koinConfiguration
import authorization.presentation.login.LoginScreen
import org.koin.compose.koinInject
import authorization.domain.repository.AppCloseRepository
import java.awt.Toolkit


@Composable
@Preview
fun App(appClose: AppCloseRepository = koinInject()) {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        Window(
            icon = painterResource("images/logo.svg"),
            onCloseRequest = { appClose.close() },
            title = "Transparent Window Example",
            state = WindowState(
                placement = WindowPlacement.Floating,
                position = WindowPosition(
                    ((screenSize.width - 3000) / 6).dp,
                    ((screenSize.height - 700) / 4).dp,
                ),
                width = screenSize.width.dp,
                height = screenSize.height.dp
            ),
            transparent = true,
            undecorated = true, //transparent window must be undecorated
        ) {
            WindowDraggableArea {
                LoginScreen()
            }
        }
}



fun main() = application {
    koinConfiguration()
    App()
}

