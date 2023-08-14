package AlertSystem.presentation

import androidx.compose.runtime.*
import common.component.AlertDialogSimple


private var showErrorMessage by mutableStateOf(false)
private var errorMessage by  mutableStateOf("")
private var showSuccessMessage by mutableStateOf(false)
private var successMessage by  mutableStateOf("")
@Composable
fun AlertMessagesConfig(){

    if (showErrorMessage) {
    ErrorMessage(errorMessage){
        showErrorMessage = false
    }
    }

    if (showSuccessMessage) {
        AlertDialogSimple(
            message = successMessage
        ) {
            showSuccessMessage = false
        }
    }
}


fun String.showErrorMessage(){
    errorMessage = this
    showErrorMessage = true
}
fun String.showSuccessMessage(){
    successMessage = this
    showSuccessMessage = true
}
