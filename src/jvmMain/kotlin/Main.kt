import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Upload
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.vector.ImageVector
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
import styles.AppColors.background
import styles.AppColors.primary
import styles.AppColors.secondary
import styles.AppColors.white
import common.component.NavigationWindow
import io.realm.kotlin.Realm
import license.presentation.AppLicensesScreen
import org.koin.compose.getKoin
import org.koin.core.context.GlobalContext.get


@Composable
@Preview
fun App(appClose: AppCloseRepository = koinInject()) {
    val navController by rememberNavController(AuthScreen.AppLicenseScreen.name)

    val windowState = rememberWindowState(
        size = DpSize(1000.dp,700.dp),
        placement = WindowPlacement.Floating,
        position = WindowPosition(Alignment.Center),
    )
    MaterialTheme(
       colors= MaterialTheme.colors.copy(primary = primary,
           secondary = secondary,
           background = background,
           surface = white,
           error = Red,
           isLight = true,
       )
    ) {
        RoundedCornerWindow(
            state= windowState,
            onClose = {appClose.close()}
        ){
            AuthNavigationHost(navController = navController,windowState)
        }
    }
}


fun main() = application {
    koinConfiguration()

    App()
}

fun closeRealmWhenApplicationClose(realm: Realm ) {
    Runtime.getRuntime().addShutdownHook(Thread {
        if (!realm.isClosed()) realm.close()
    })
}


enum class AuthScreen(
    val label: String,
    val icon: ImageVector
) {
    LoginAuthScreen(
        label = "Home",
        icon = Icons.Filled.Home
    ),
    SignupAuthScreen(
        label = "Notifications",
        icon = Icons.Filled.Notifications
    ),
    SystemScreen(
        label = "System",
        icon = Icons.Filled.Notifications
    ),
    AppLicenseScreen(
    label = "System",
    icon = Icons.Filled.Notifications
    )
}


@Composable
fun AuthNavigationHost(
    navController: NavController<String>,
    windowState:WindowState
) {
    NavigationHost(navController) {
        composable(AuthScreen.LoginAuthScreen.name) {
            windowState.placement = WindowPlacement.Floating
            windowState.size = DpSize(1000.dp,700.dp)
            LoginScreen(navController)
        }

        composable(AuthScreen.SignupAuthScreen.name) {
            windowState.size= DpSize(1100.dp,750.dp)
            RegisterScreen(navController)
        }

        composable(AuthScreen.SystemScreen.name) {
            windowState.size= DpSize(1100.dp,750.dp)
            NavigationWindow(navController,windowState)
        }

        composable(AuthScreen.AppLicenseScreen.name) {
            windowState.size= DpSize(420.dp,320.dp)
            windowState.placement = WindowPlacement.Floating
            AppLicensesScreen(navController)
        }
    }.build()
}

