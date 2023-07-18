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
import authorization.domain.model.Jobs
import authorization.domain.model.Systems
import authorization.domain.model.User
import authorization.domain.repository.AuthenticationRepository
import authorization.domain.usecase.GetUser
import authorization.domain.usecase.LogoutUseCase
import authorization.presentation.accountsPermissions.AccountsPermissionsScreen
import authorization.presentation.login.LoginViewModel
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
import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.presentation.sons_of_officers.SonsOfOfficersScreen
import features.sons_of_officers.presentation.add_sons_of_officers.AddSonsOfOfficersScreen
import org.koin.compose.koinInject
import styles.CairoTypography
import utils.Resource
import utils.UserAuthSystem
import utils.getUserAuth
import kotlin.system.exitProcess

@Composable
fun NavigationWindow(
    authNavController: NavController<String>,
    windowState: WindowState,
    viewModel: LoginViewModel = koinInject(),
    ) {
//    val screens = listOf<Screens>(
//        Screens.HomeScreen(),
//        Screens.ContractsScreen(),
//        Screens.SonsOfOfficersScreen(),
//        Screens.CoursesScreen() ,
//        Screens.AccountsPermissionsScreen()
//    )
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
    var superAdminPermission = userAuthSystem.canChangeAccountsPermission()
    userAuthSystem.currentUser // replace with your own code to get the current user
    // Get the current user's authentication status for each screen
    val screenAuthStatus = mapOf(
        Systems.Home to userAuthSystem.canAccessScreen(Systems.Home),
        Systems.Contracts to userAuthSystem.canAccessScreen(Systems.Contracts),
        Systems.SonsOfOfficers to userAuthSystem.canAccessScreen(Systems.SonsOfOfficers),
        Systems.Courses to userAuthSystem.canAccessScreen(Systems.Courses)
    )

// Display the screens based on the user's authentication status
    val screens = mutableListOf<Screens>()

    if (screenAuthStatus[Systems.Home] == true) {
        screens.add(Screens.HomeScreen())
    }

    if (screenAuthStatus[Systems.Contracts] == true) {
        screens.add(Screens.ContractsScreen())
    }

    if (screenAuthStatus[Systems.SonsOfOfficers] == true) {
        screens.add(Screens.SonsOfOfficersScreen())
    }

    if (screenAuthStatus[Systems.Courses] == true) {
        screens.add(Screens.CoursesScreen())
    }

    if (superAdminPermission){
            screens.add(Screens.AccountsPermissionsScreen())
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
                            modifier = Modifier.fillMaxWidth(),
                            selected = currentScreen == it,
                            onClick = {
                                navController.navigate(it)
                            },
                            icon = {
                                Icon(
                                    imageVector = it.icon,
                                    contentDescription = it.label
                                )
                            },


                            label = {
                                Text(it.label, style = CairoTypography.body2, fontWeight = FontWeight.Bold)
                            },
                            alwaysShowLabel = true,
                        )
                    }
                    Spacer(modifier = Modifier.size(200.dp))

                    IconButton(
                        onClick = {
                            //    val user = User("123", "John Doe", "password", Jobs.Admin, listOf(Systems.Contracts, Systems.Home))
// set the currentUser property to the current user
//                             userAuthSystem.currentUser =User("", "", "", Jobs.None, listOf())
                            // Handle logout button click
                            viewModel.loginState.value = (Resource.Loading(User("","","", Jobs.None, emptyList())))

                            authNavController.navigate(AuthScreen.LoginAuthScreen.name)
                  print("clickkkkkkkkkkkk")

                        },
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.ExitToApp, contentDescription = "Exit app")
                            Text(text = "تسجيل الخروج", style = CairoTypography.h4, modifier = Modifier.padding(start = 8.dp))
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


sealed class Screens(val label: String, val icon: ImageVector,val type:Int) {
    class HomeScreen : Screens(label = "الصفحة الرئيسية", icon = Icons.Filled.Home,0)
    data class AddSonsOfOfficersScreen(val mode: ScreenMode = ADD,val person:Person? = null) : Screens(label = "منظومة ابناء الضباط /إضافة", icon = Icons.Filled.PersonAdd,1)
    class SonsOfOfficersScreen : Screens(label = "منظومة ابناء الضباط", icon = Icons.Filled.Policy,2)
    data class AddContractsScreen(val mode: ScreenMode = ADD,val contract: Contract? =null): Screens(label = "منظومة  العقود/اضافه", icon = Icons.Filled.PersonAdd,3)
    class ContractsScreen : Screens(label = "منظومة  العقود", icon = Icons.Filled.FilePresent,4)
    data class AddCoursesScreen(val mode: ScreenMode = ADD,val course:Course? = null): Screens(label = "منظومة  الدورات-إضافة", icon = Icons.Filled.PersonAdd,5)
    class CoursesScreen : Screens(label = "منظومة  الدورات", icon = Icons.Filled.PersonAdd,6)
    class AccountsPermissionsScreen : Screens(label = "الحسابات", icon = Icons.Filled.AccountBalance,6)

}

enum class ScreenMode{
    EDIT,ADD,DELET
}


@Composable
fun SystemNavigationHost(
    navController: NavController<Screens>,
    windowState: WindowState
) {
    NavigationHost(navController,{currentScreen,route-> currentScreen.javaClass == route.javaClass}) {
        composable(Screens.HomeScreen()) {
            println("Screen")
            HomeScreen(navController)
        }

        composable(Screens.AddSonsOfOfficersScreen()) {
            val screenDetails = navController.currentScreen.value as Screens.AddSonsOfOfficersScreen
            println("it's add sons ${screenDetails.mode}")
            AddSonsOfOfficersScreen(navController,screenDetails.person, mode = screenDetails.mode)
        }
        composable(Screens.SonsOfOfficersScreen()) {
            windowState.placement = WindowPlacement.Fullscreen
            SonsOfOfficersScreen(navController)
        }
        composable(Screens.AddContractsScreen()) {
//            windowState.placement = WindowPlacement.Fullscreen
            val screenDetails = navController.currentScreen.value as Screens.AddContractsScreen
            println("it's add sons ${screenDetails.mode}")
            AddContractsScreen(navController,screenDetails.contract, mode = screenDetails.mode)

//            AddContractsScreen(navController)
        }
        composable(Screens.ContractsScreen()) {
            windowState.placement = WindowPlacement.Fullscreen
            ContractsScreen(navController)
        }
        composable(Screens.AddCoursesScreen()) {
            windowState.placement = WindowPlacement.Fullscreen
            val screenDetails = navController.currentScreen.value as Screens.AddCoursesScreen
            AddCoursesScreen(navController,screenDetails.course, mode = screenDetails.mode)
        }
        composable(Screens.CoursesScreen()) {
            windowState.placement = WindowPlacement.Fullscreen
            CoursesScreen(navController)
        }
        composable(Screens.AccountsPermissionsScreen()) {
            windowState.placement = WindowPlacement.Fullscreen
            AccountsPermissionsScreen(navController)
        }
    }.build()
}
