package common.component

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import styles.CairoTypography

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlertDialogSimple(message: String,
                      onDismiss: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = {
            coroutineScope.launch {
                delay(500) // Delay for 2 seconds (adjust as desired)
                onDismiss()
            }
        },
//        title = {
//            Text(text = "رسالة تنبيه!",)
//        },
        text = {
            Text(text = message,style = CairoTypography.h3 , fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,               textAlign = TextAlign.Center
            )
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