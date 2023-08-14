package AlertSystem.presentation

import androidx.compose.runtime.Composable
import common.component.AlertDialogSimple

@Composable
fun ErrorMessage(message:String,onDismiss:()->Unit){
    AlertDialogSimple(
        message = message
    ) {
        onDismiss()
    }
}