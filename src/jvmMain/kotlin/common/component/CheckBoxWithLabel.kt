package common.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import styles.AppColors.blue
import styles.CairoTypography

@Composable
fun CheckBoxWithLabel(
    label: String,
    checked: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            colors = CheckboxDefaults.colors(
                checkedColor = blue,
                uncheckedColor = Color.Gray,
                checkmarkColor = Color.White,
                disabledColor = Color.LightGray,
            ),
            checked = checked,
            onCheckedChange = onCheckedChange,
        )
        Text(
            label,
            style = CairoTypography.h4,
            modifier = Modifier.padding(horizontal = 8.dp),
        )
    }
}

@Preview
@Composable
fun PreviewCheckBoxWithLabel() {
    CheckBoxWithLabel(
        "Hello",
        onCheckedChange = { println(it) }
    )
}
