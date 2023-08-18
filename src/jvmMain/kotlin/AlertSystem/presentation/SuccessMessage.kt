package AlertSystem.presentation

import androidx.compose.runtime.Composable
import common.component.AlertDialogSimple
import common.component.MessageType

@Composable
fun SuccessMessage(message:String,onDismiss:()->Unit){
    AlertDialogSimple(
        message = message,
        messageType = MessageType.SUCCESS,  onDismiss = { showSuccessMessage = false }
    )
}