package common.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import styles.CairoTypography
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HeadLineWithDate(
    text: String,
    date: String,
){

//    val currentDateTime = getCurrentDateTime()
    var currentDateTime by remember { mutableStateOf(getCurrentDateTime()) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000) // Wait 1 second
            currentDateTime = getCurrentDateTime() // Update currentDateTime
        }
    }
    Spacer(modifier = Modifier.size(width = 0.dp,height = 35.dp))
    Row (        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp,).sizeIn(maxHeight = 40.dp),

        horizontalArrangement = Arrangement.SpaceBetween,){
        Text(
            text = text,
            color = Color(0xff3B5EA1),
            style = CairoTypography.h2,
        )
        Text(
            text = currentDateTime,
            color = Color(0xff3B5EA1),
            style = CairoTypography.h3,
        )
    }
    Spacer(modifier = Modifier.size(width = 0.dp,height = 15.dp))

}

fun getCurrentDateTime(): String {
    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd    HH:mm:ss")
    return currentDateTime.format(formatter)
}