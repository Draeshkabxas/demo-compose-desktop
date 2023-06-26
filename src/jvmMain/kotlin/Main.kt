import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import di.koinConfiguration
import authorization.presentation.login.LoginScreen
import org.koin.compose.koinInject
import authorization.domain.repository.AppCloseRepository
import authorization.presentation.register.RegisterScreen
import common.component.RoundedCornerWindow
import navcontroller.NavController
import navcontroller.NavigationHost
import navcontroller.composable
import navcontroller.rememberNavController
import java.awt.Toolkit


@Composable
@Preview
fun App(appClose: AppCloseRepository = koinInject()) {
    val navController by rememberNavController(Screen.LoginScreen.name)
    val windowState = rememberWindowState(size = DpSize(1000.dp,700.dp))
    MaterialTheme {
        RoundedCornerWindow(
            size= windowState.size,
            onClose = {appClose.close()}
        ){
            CustomNavigationHost(navController = navController,windowState)
        }
    }
}


fun main() = application {
    koinConfiguration()
    App()
}


enum class Screen(
    val label: String,
    val icon: ImageVector
) {
    LoginScreen(
        label = "Home",
        icon = Icons.Filled.Home
    ),
    SignupScreen(
        label = "Notifications",
        icon = Icons.Filled.Notifications
    ),
    SettingsScreen(
        label = "Settings",
        icon = Icons.Filled.Settings
    ),
    ProfileScreens(
        label = "User Profile",
        icon = Icons.Filled.VerifiedUser
    )
}


@Composable
fun CustomNavigationHost(
    navController: NavController,
    windowState:WindowState
) {
    NavigationHost(navController) {
        composable(Screen.LoginScreen.name) {
            LoginScreen(navController)
        }

        composable(Screen.SignupScreen.name) {
            RegisterScreen(navController)
        }

    }.build()
}

