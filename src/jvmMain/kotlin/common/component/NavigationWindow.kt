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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import authorization.domain.model.Systems
import authorization.presentation.accountsPermissions.AccountsPermissionsScreen
import common.component.ScreenMode.ADD
import features.contracts.domain.model.Contract
import features.contracts.presentation.add_contracts.AddContractsScreen
import features.contracts.presentation.contracts.ContractsScreen
import features.courses.domain.model.Course
import features.courses.presentation.add_courses.AddCoursesScreen
import features.courses.presentation.courses.CoursesScreen
import navcontroller.NavController
import navcontroller.NavigationHost
import navcontroller.composable
import navcontroller.rememberNavController
import styles.AppColors.background
import features.home.presentation.HomeScreen
import features.results.domain.model.Results
import features.results.presentation.add_results.AddResultsScreen
import features.results.presentation.results.ResultsScreen
import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.presentation.sons_of_officers.SonsOfOfficersScreen
import features.sons_of_officers.presentation.add_sons_of_officers.AddSonsOfOfficersScreen
import styles.AppColors.blue
import styles.CairoTypography
import utils.getUserAuth
import kotlin.system.exitProcess

@Composable
fun NavigationWindow(
    authNavController: NavController<String>,
    windowState: WindowState,
) {
//    val screens = listOf<Screens>(
//        Screens.HomeScreen(),
//        Screens.ContractsScreen(),
//        Screens.SonsOfOfficersScreen(),
//        Screens.CoursesScreen() ,
//        Screens.AccountsPermissionsScreen()
//
    val navController by rememberNavController<Screens>(Screens.HomeScreen())
    val currentScreen by remember {
        navController.currentScreen
    }

    //primmisions
    // create a user
//    val user = User("123", "John Doe", "password", Jobs.Admin, listOf(Systems.Contracts, Systems.Home))
// set the currentUser property to the current user
    val userAuthSystem = getUserAuth()
    var canEditPermission = userAuthSystem.canEdit()
    val superAdminPermission = userAuthSystem.canChangeAccountsPermission()
    userAuthSystem.currentUser // replace with your own code to get the current user
    // Get the current user's authentication status for each screen
    val screenAuthStatus = mapOf(
        Screens.HomeScreen() to userAuthSystem.canAccessScreen(Systems.Home),
        Screens.ContractsScreen() to userAuthSystem.canAccessScreen(Systems.Contracts),
        Screens.SonsOfOfficersScreen() to userAuthSystem.canAccessScreen(Systems.SonsOfOfficers),
        Screens.CoursesScreen() to userAuthSystem.canAccessScreen(Systems.Courses),
        Screens.ResultsScreen() to userAuthSystem.canAccessScreen(Systems.Results),
        Screens.AccountsPermissionsScreen() to superAdminPermission
    )

    // Display the screens based on the user's authentication status
    val screensThatCanUserAccess = screenAuthStatus.filter { it.value }.keys.toList()




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
                    modifier = Modifier.fillMaxHeight().width(140.dp),
                    backgroundColor = blue

                ) {
                    screensThatCanUserAccess.forEach {
                        NavigationRailItem(
                            modifier = Modifier.fillMaxWidth(),
                            selected = currentScreen == it,
                            onClick = {
                                navController.navigate(it)
                            },
                            icon = {
                                Icon(
                                    imageVector = it.icon,
                                    contentDescription = it.label,
                                    tint= Color.White
                                )
                            },


                            label = {
                                Text(it.label, style = CairoTypography.body2, fontWeight = FontWeight.Bold,color= Color.White)
                            },
                            alwaysShowLabel = true,
                        )
                    }
                    Spacer(modifier = Modifier.size(200.dp))

                    IconButton(
                        onClick = {
                            userAuthSystem.logout()
                            // Handle logout button click
                            authNavController.navigate(AuthScreen.LoginAuthScreen.name)
                            print("clickkkkkkkkkkkk")
                        },
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.ExitToApp, contentDescription = "Exit app")
                            Text(
                                text = "تسجيل الخروج",
                                style = CairoTypography.h4,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
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
                        val isWindowInFullScreen = windowState.placement == WindowPlacement.Fullscreen
                        IconButton(
                            onClick = {
                                if (isWindowInFullScreen)
                                    windowState.placement = WindowPlacement.Floating
                                else
                                    windowState.placement = WindowPlacement.Fullscreen
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
            }
        }
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
    AddContractsScreen(
        label = "منظومة  العقود/اضافه",
        icon = Icons.Filled.PersonAdd
    ),
    ContractsScreen(
        label = "منظومة  العقود",
        icon = Icons.Filled.PersonAdd
    ),
    AddCoursesScreen(
        label = "منظومة  الدورات-إضافة",
        icon = Icons.Filled.PersonAdd
    ),
    CoursesScreen(
        label = "منظومة  الدورات",
        icon = Icons.Filled.PersonAdd
    ),
}


sealed class Screens(val label: String, val icon: ImageVector, val type: Int) {
    class HomeScreen : Screens(label = "الصفحة الرئيسية", icon = Icons.Filled.Home, 0)
    data class AddSonsOfOfficersScreen(val mode: ScreenMode = ADD, val person: Person? = null) :
        Screens(label = "منظومة ابناء الضباط /إضافة", icon = Icons.Filled.PersonAdd, 1)

    class SonsOfOfficersScreen : Screens(label = "منظومة ابناء الضباط", icon = Icons.Filled.Policy, 2)
    data class AddContractsScreen(val mode: ScreenMode = ADD, val contract: Contract? = null) :
        Screens(label = "منظومة  العقود/اضافه", icon = Icons.Filled.PersonAdd, 3)

    class ContractsScreen : Screens(label = "منظومة  العقود", icon = Icons.Filled.FilePresent, 4)
    data class AddCoursesScreen(val mode: ScreenMode = ADD, val course: Course? = null) :
        Screens(label = "منظومة  الدورات-إضافة", icon = Icons.Filled.PersonAdd, 5)

    class CoursesScreen : Screens(label = "منظومة  الدورات", icon = Icons.Filled.PersonAdd, 6)
    class AccountsPermissionsScreen : Screens(label = "الحسابات", icon = Icons.Filled.AccountBalance, 7)

    data class AddResultsScreen(val mode: ScreenMode = ADD, val results: Results? = null) :
        Screens(label = "منظومة  نتائج التحاليل/إضافه", icon = Icons.Filled.PersonAdd, 8)

    class ResultsScreen : Screens(label = "منظومة  نتائج التحاليل", icon = Icons.Filled.Notes, 9)

}

enum class ScreenMode {
    EDIT, ADD, DELET
}


@Composable
fun SystemNavigationHost(
    navController: NavController<Screens>,
    windowState: WindowState
) {
    NavigationHost(navController, { currentScreen, route -> currentScreen.javaClass == route.javaClass }) {
        composable(Screens.HomeScreen()) {
            println("Screen")
            HomeScreen(navController)
        }

        composable(Screens.AddSonsOfOfficersScreen()) {
            val screenDetails = navController.currentScreen.value as Screens.AddSonsOfOfficersScreen
            println("it's add sons ${screenDetails.mode}")
            AddSonsOfOfficersScreen(navController, screenDetails.person, mode = screenDetails.mode)
        }
        composable(Screens.SonsOfOfficersScreen()) {
            windowState.placement = WindowPlacement.Fullscreen
            SonsOfOfficersScreen(navController)
        }
        composable(Screens.AddContractsScreen()) {
//            windowState.placement = WindowPlacement.Fullscreen
            val screenDetails = navController.currentScreen.value as Screens.AddContractsScreen
            println("it's add sons ${screenDetails.mode}")
            AddContractsScreen(navController, screenDetails.contract, mode = screenDetails.mode)

//            AddContractsScreen(navController)
        }
        composable(Screens.ContractsScreen()) {
            windowState.placement = WindowPlacement.Fullscreen
            ContractsScreen(navController)
        }
        composable(Screens.AddCoursesScreen()) {
            windowState.placement = WindowPlacement.Fullscreen
            val screenDetails = navController.currentScreen.value as Screens.AddCoursesScreen
            AddCoursesScreen(navController, screenDetails.course, mode = screenDetails.mode)
        }
        composable(Screens.CoursesScreen()) {
            windowState.placement = WindowPlacement.Fullscreen
            CoursesScreen(navController)
        }
        composable(Screens.AccountsPermissionsScreen()) {
            windowState.placement = WindowPlacement.Fullscreen
            AccountsPermissionsScreen(navController)
        }
        composable(Screens.ResultsScreen()) {
            windowState.placement = WindowPlacement.Fullscreen
            ResultsScreen(navController)
        }
        composable(Screens.AddResultsScreen()) {
            windowState.placement = WindowPlacement.Fullscreen
            val screenDetails = navController.currentScreen.value as Screens.AddResultsScreen
            AddResultsScreen(navController, screenDetails.results, mode = screenDetails.mode)
        }
    }.build()
}
