package common.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import styles.CairoTypography

@Composable
fun HeadLineWithDate(
    text: String,
    date: String,
){
    Row (        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 35.dp).sizeIn(maxHeight = 50.dp),

        horizontalArrangement = Arrangement.SpaceBetween,){
        Text(
            text = text,
            color = Color(0xff3B5EA1),
            style = CairoTypography.h3,
        )
        Text(
            text = date,
            color = Color(0xff3B5EA1),
            style = CairoTypography.h4,
        )
    }
}