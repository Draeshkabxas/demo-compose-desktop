package common.component

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import features.sons_of_officers.domain.model.Person
import styles.CairoTypography

@Composable
fun ItemMenu(
    modifier: Modifier = Modifier,
    showMenu: MutableState<Boolean>,
    onEdit: () -> Unit,
    onRemove: () -> Unit,
) {

    if (showMenu.value) {
        DropdownMenu(
            expanded = showMenu.value,
            onDismissRequest = { showMenu.value = false },
            offset = DpOffset(1200.dp, (-50).dp)
        ) {
            DropdownMenuItem(onClick = {
                // Handle edit action
                onEdit()
                showMenu.value = false
            }) {
                Text("تعديل", style = CairoTypography.h4)
            }
            DropdownMenuItem(onClick = {
                // Handle delete action
                onRemove()
                showMenu.value = false
            }) {

                Text("مسح", style = CairoTypography.h4)
            }
        }
    }
}
