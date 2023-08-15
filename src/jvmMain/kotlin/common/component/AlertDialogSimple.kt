package common.component

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import styles.CairoTypography

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlertDialogSimple(message: String,
                      onDismiss: () -> Unit,
                      messageType: MessageType,
                      ) {
    val coroutineScope = rememberCoroutineScope()
    val backGroundColor = when (messageType) {
        MessageType.ERROR -> Color.Red.copy(alpha = 0.46f)
        MessageType.SUCCESS -> Color.Green.copy(alpha = 0.46f)
    }
    AlertDialog(
        backgroundColor =backGroundColor ,
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
            val textColor = when (messageType) {
                MessageType.ERROR -> Color.White
                MessageType.SUCCESS -> Color.White
            }
            Text(
                text = message,
                style = CairoTypography.h3,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = textColor
            )
        },
        buttons = {}
    )

}
enum class MessageType {
    ERROR,
    SUCCESS
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