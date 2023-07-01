package common.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import styles.AppColors.primary

@Composable
fun <T> RoundedDropdownMenu(
    items: List<T>,
    onSelectItem: (T)->Unit,
    label:@Composable (T) -> Unit,
    itemContent: @Composable (T) -> Unit
) {
    var selectedItem by remember { mutableStateOf(items[0]) }
    var expanded by remember { mutableStateOf(false) }
    val icon = if (!expanded) Icons.Outlined.ArrowDropDown else Icons.Outlined.ArrowDropUp

    Column(
        modifier = Modifier.sizeIn(maxWidth = 200.dp),
    ) {
        Button(
            onClick = { expanded = !expanded },
            shape = RoundedCornerShape(20),
            border = BorderStroke(2.dp, primary),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = primary),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.sizeIn(minWidth = 300.dp)
            ) {
                label(selectedItem)
                Icon(icon, modifier = Modifier.size(30.dp), contentDescription = "Dropdown menu icon")
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.sizeIn(minWidth = 200.dp)
        ) {
            items.forEach { item ->
                DropdownMenuItem(onClick = {
                    onSelectItem(item)
                    selectedItem = item
                    expanded = false
                }) {
                    itemContent(item)
                }
            }
        }
    }
}
