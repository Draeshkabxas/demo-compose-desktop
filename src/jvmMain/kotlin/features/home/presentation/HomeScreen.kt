package features.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.component.Screens
import navcontroller.NavController
import common.component.SystemScreen

@Composable
fun HomeScreen(
    navController: NavController<Screens>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(navController.currentScreen.value.label)
        Button(
            onClick = {
                navController.navigate(Screens.AddSonsOfOfficersScreen())
            }) {
            Text("Navigate to Add Sons")
        }

    }
}