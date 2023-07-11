package features.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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