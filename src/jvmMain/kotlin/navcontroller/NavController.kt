package navcontroller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable

/**
 * NavController Class
 */
class NavController<T>(
    private val startDestination: T,
    private var backStackScreens: MutableSet<T> = mutableSetOf()
) {
    // Variable to store the state of the current screen
    var currentScreen: MutableState<T> = mutableStateOf(startDestination)
    var currentScreenValue = currentScreen.value

    // Function to handle the navigation between the screen
    fun navigate(route: T) {
        if (route == currentScreen.value) return

        if (backStackScreens.contains(currentScreen.value) && currentScreen.value != startDestination) {
            backStackScreens.remove(currentScreen.value)
        }

        if (route == startDestination) {
            backStackScreens = mutableSetOf()
        } else {
            backStackScreens.add(currentScreen.value)
        }

        currentScreen.value = route

    }

    fun navigateReplacement(route: T) {
        val lastScreen = currentScreen.value
        if (route == currentScreen.value) return

        if (currentScreen.value != startDestination) {
            backStackScreens.remove(lastScreen)
        }

        if (route == startDestination) {
            backStackScreens = mutableSetOf()
        } else {
            backStackScreens.add(currentScreen.value)
        }
        currentScreen.value = route
    }

    // Function to handle the back
    fun navigateBack() {
        if (backStackScreens.isEmpty()) return
        currentScreen.value = backStackScreens.last()
        backStackScreens.remove(currentScreen.value)

    }
}


/**
 * Composable to remember the state of the navController
 */
@Composable
fun <T> rememberNavController(
    startDestination: T,
    backStackScreens: MutableSet<T> = mutableSetOf()
): MutableState<NavController<T>> = rememberSaveable {
    mutableStateOf(NavController(startDestination, backStackScreens))
}

