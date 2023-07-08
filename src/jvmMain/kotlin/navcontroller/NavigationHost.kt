package navcontroller

import androidx.compose.runtime.Composable

/**
 * NavigationHost class
 */
class NavigationHost<T>(
    val navController: NavController<T>,
    val contents: @Composable NavigationGraphBuilder<T>.() -> Unit
) {

    @Composable
    fun build() {
        NavigationGraphBuilder<T>(navController,contents = contents).renderContents()
    }


}

class NavigationGraphBuilder<T>(
    val navController: NavController<T>,
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
    if (navController.currentScreen.value == route) {
        content()
    }

}