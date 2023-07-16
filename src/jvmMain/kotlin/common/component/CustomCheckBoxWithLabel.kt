package common.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import styles.AppColors.blue
import styles.CairoTypography

@Composable
fun CustomCheckboxWithLabel(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    labelColor: Color = MaterialTheme.colors.onSurface,
    checkboxColor: Color = MaterialTheme.colors.primary,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable(onClick = { onCheckedChange(!checked) })
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                uncheckedColor = checkboxColor,
                checkedColor = checkboxColor,
                checkmarkColor = Color.White
            )
        )
        Text(
            text = label,
            color = labelColor,
            style = CairoTypography.body1,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}