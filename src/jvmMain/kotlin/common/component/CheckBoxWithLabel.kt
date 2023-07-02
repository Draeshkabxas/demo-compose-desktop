package common.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            checked = checked,
            onCheckedChange = onCheckedChange,
        )
        Text(
            label,
            style = CairoTypography.body1,
            modifier = Modifier.padding(horizontal = 5.dp),
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
