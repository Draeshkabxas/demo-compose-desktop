package AlertSystem.presentation

import androidx.compose.runtime.Composable
import common.component.AlertDialogSimple

@Composable
fun SuccessMessage(message:String,onDismiss:()->Unit){
    AlertDialogSimple(
        message = message
    ) {
        onDismiss()
    }
}