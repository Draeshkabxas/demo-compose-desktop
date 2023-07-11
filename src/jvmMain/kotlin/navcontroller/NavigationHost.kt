package navcontroller

import androidx.compose.runtime.Composable

/**
 * NavigationHost class
 */
class NavigationHost<T>(
    val navController: NavController<T>,
    private val condition: (T,T) -> Boolean = {currentScreen,route->currentScreen == route },
    val contents: @Composable NavigationGraphBuilder<T>.() -> Unit
) {

    @Composable
    fun build() {
        NavigationGraphBuilder<T>(navController,condition,contents = contents).renderContents()
    }


}

class NavigationGraphBuilder<T>(
    val navController: NavController<T>,
    val condition: (T,T) -> Boolean,
    val contents:@Composable NavigationGraphBuilder<T>.() -> Unit
) {
    @Composable
    fun renderContents() {
        contents(this)
    }
}

/**
 * Composable to build the Navigation Host
 */
@Composable
fun <T> NavigationGraphBuilder<T>.composable(
    route: T,
    content: @Composable () -> Unit
) {
    if (condition(navController.currentScreen.value,route)) {
        content()
    }
}