package system.presentation.sons_of_officers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import navcontroller.NavController
import system.presentation.common.component.CustomOutlinedTextField
import system.presentation.common.component.Section

@Composable
fun AddSonsOfOfficersScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Section("البيانات الشخصية",3){
            repeat(10){
                item {
                    CustomOutlinedTextField(errorMessage = "")
                }
            }
        }
    }
}
