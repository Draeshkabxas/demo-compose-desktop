package common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import navcontroller.NavController
import navcontroller.NavigationHost
import navcontroller.composable
import navcontroller.rememberNavController
import styles.AppColors.background
import features.home.presentation.HomeScreen
import features.sons_of_officers.presentation.SonsOfOfficersScreen
import features.sons_of_officers.presentation.add_sons_of_officers.AddSonsOfOfficersScreen
import kotlin.system.exitProcess

@Composable
fun NavigationWindow(
    authNavController: NavController,
    windowState: WindowState
) {
    val screens = SystemScreen.values().toList()
    val navController by rememberNavController(SystemScreen.HomeScreen.name)
    val currentScreen by remember {
        navController.currentScreen
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .shadow(3.dp, RoundedCornerShape(20.dp)),
        color = background,
        shape = RoundedCornerShape(20.dp), //window has round corners now
        elevation = 40.dp
    ) {
        CompositionLocalProvider(
            LocalLayoutDirection provides LayoutDirection.Rtl // Set layout direction to RTL
        ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationRail(
                modifier = Modifier.fillMaxHeight().width(140.dp)

            ) {
                screens.forEach {
                    NavigationRailItem(
                        selected = currentScreen == it.name,
                        icon = {
                            Icon(
                                imageVector = it.icon,
                                contentDescription = it.label
                            )
                        },
                        label = {
                            Text(it.label)
                        },
                        alwaysShowLabel = false,
                        onClick = {
                            navController.navigate(it.name)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.size(5.dp))
            Box(
                modifier = Modifier.fillMaxHeight(),
            ) {
                SystemNavigationHost(navController = navController, windowState)
                Row(
                    modifier = Modifier.align(Alignment.TopEnd),
                ) {
                    IconButton(onClick = { windowState.isMinimized = true }) {
                        Icon(
                            painter = painterResource("images/minimize.svg"),
                            contentDescription = "Minimize Application",
                            tint = Color.Gray.copy(alpha = 0.4f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    val isWindowInFullScreen = windowState.placement == WindowPlacement.Maximized
                    IconButton(
                        onClick = {
                            if (isWindowInFullScreen)
                                windowState.placement = WindowPlacement.Floating
                            else
                                windowState.placement = WindowPlacement.Maximized
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
                    IconButton(onClick = { exitProcess(0) }) {
                        Image(
                            painter = painterResource("images/exit.svg"),
                            contentDescription = "Exit Application",
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }}
    }
}


enum class SystemScreen(
    val label: String,
    val icon: ImageVector
) {
    HomeScreen(
        label = "الصفحة الرئيسية",
        icon = Icons.Filled.Home
    ),
    AddSonsOfOfficersScreen(
        label = "منظومة ابناء الضباط",
        icon = Icons.Filled.PersonAdd
    ),
    SonsOfOfficersScreen(
        label = "منظومة ابناء الضباط",
        icon = Icons.Filled.PersonAdd
    ),
}


@Composable
fun SystemNavigationHost(
    navController: NavController,
    windowState: WindowState
) {
    NavigationHost(navController) {
        composable(SystemScreen.HomeScreen.name) {
            HomeScreen(navController)
        }

        composable(SystemScreen.AddSonsOfOfficersScreen.name) {
            windowState.size = DpSize(1100.dp, 950.dp)
            AddSonsOfOfficersScreen(navController)
        }
        composable(SystemScreen.SonsOfOfficersScreen.name) {
            windowState.size = DpSize(1100.dp, 950.dp)
            SonsOfOfficersScreen(navController)
        }

    }.build()
}