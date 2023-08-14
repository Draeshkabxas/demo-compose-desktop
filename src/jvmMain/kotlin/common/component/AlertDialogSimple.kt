package common.component

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlertDialogSimple(message: String,
                      onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "رسالة تنبيه!")
        },
        text = {
            Text(text = message)
        },
        buttons = {}
    )

}


//showDialog = true
//
//var showDialog by remember { mutableStateOf(false) }
//if (showDialog) {
//    AlertDialogSimple(
//        message = "This is a custom alert dialog."
//    ) {
//        showDialog = false
//    }
//}