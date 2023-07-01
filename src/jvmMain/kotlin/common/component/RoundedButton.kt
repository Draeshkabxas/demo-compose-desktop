package common.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import styles.CairoTypography
import styles.AppColors.primary
import styles.AppColors.white

@Composable
fun RoundedButton(onClick: () -> Unit, text: String) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(20),
        modifier = Modifier.padding(16.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = primary),
    ) {
        Text(text = text, style = CairoTypography.h2.copy(color= white))
    }
}
