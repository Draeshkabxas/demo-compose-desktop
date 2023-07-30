package common.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import styles.CairoTypography
import styles.AppColors.hintText
import styles.AppColors.primary
@Composable
fun SelectorWithLabel(
    label: String,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedIndex = items.indexOf(selectedItem)

    Column(modifier = Modifier.padding(horizontal = 1.dp)) {
        Row(modifier = Modifier.fillMaxHeight().padding(vertical = 20.dp)
            ,verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Text(text = label,
                style = CairoTypography.h4)
            Spacer(Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .border(
                        width = 1.dp,
                        color = if (expanded) Color.Gray else Color.Black,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable(onClick = { expanded = true })
            ) {
                Row (horizontalArrangement = Arrangement.Center){
                    Spacer(Modifier.width(14.dp))

                    Text(
                        text = selectedItem,
                        style = CairoTypography.body1,
                        modifier = Modifier.padding(vertical = 16.dp)

                    )
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.padding(vertical = 16.dp).size(20.dp)
                    )
                    Spacer(Modifier.width(14.dp))

                }
                DropdownMenu(modifier = Modifier.sizeIn(maxHeight = 500.dp),
                    focusable=true,
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    items.forEachIndexed { index, item ->
                        DropdownMenuItem(
                            onClick = {
                                onItemSelected(item)
                                expanded = false
                            }
                        ) {
                            Text(text = item)
                            if (index == selectedIndex) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}