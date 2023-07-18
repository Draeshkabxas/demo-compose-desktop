package features.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.component.DirectoryDialog
import common.component.Screens
import navcontroller.NavController
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    navController: NavController<Screens>,
    viewModel:HomeViewModel = koinInject()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var showSaveBackupDirectoryPathDialog by remember { mutableStateOf(false) }
        var showGetBackupDirectoryPathDialog by remember { mutableStateOf(false) }

        Text(navController.currentScreen.value.label)
        Button(
            onClick = {
                navController.navigate(Screens.AddSonsOfOfficersScreen())
            }) {
            Text("Navigate to Add Sons")
        }
        Button(
            onClick = {
                showSaveBackupDirectoryPathDialog = true
            }) {
            Text("Save BackUp")
        }
        if (showSaveBackupDirectoryPathDialog) {
            DirectoryDialog(
                onApproved = { filePath ->
                    viewModel.saveBackupTheDB(dirPath = filePath, onError = {}, onLoading = {}, onSuccess = {})
                    showSaveBackupDirectoryPathDialog = false
                },
                onCanceled = {
                    showSaveBackupDirectoryPathDialog = false
                    println("on canceled")
                },
                onError = {
                    println("on onError")
                }
            )
        }
        Button(
            onClick = {
                showGetBackupDirectoryPathDialog = true
            }) {
            Text("Get BackUp")
        }
        if (showGetBackupDirectoryPathDialog) {
            DirectoryDialog(
                onApproved = { filePath ->
                    viewModel.getBackup(dirPath = filePath)
                    showGetBackupDirectoryPathDialog = false
                },
                onCanceled = {
                    showGetBackupDirectoryPathDialog = false
                    println("on canceled")
                },
                onError = {
                    println("on onError")
                }
            )
        }


    }
}