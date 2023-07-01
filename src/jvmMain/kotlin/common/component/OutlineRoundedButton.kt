package common.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import styles.CairoTypography
import styles.AppColors.primary


@Composable
fun OutlineRoundedButton(onClick: () -> Unit, text: String) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(20),
        border = BorderStroke(2.dp, primary),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = primary),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = text, style = CairoTypography.h2.copy(color = primary))
    }
}
