package common.component

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import features.sons_of_officers.domain.model.Person
import styles.AppColors
import styles.CairoTypography
import utils.getUserAuth

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemMenu(
    modifier: Modifier = Modifier,
    showMenu: MutableState<Boolean>,
    onEdit: () -> Unit,
    onRemove: () -> Unit,
    showDialog: MutableState<Boolean>,
//    onCancel: () -> Unit,
    alertText:String
) {
    val userAuthSystem = getUserAuth()
    var canEditPermission = userAuthSystem.canEdit()
    var superAdmin = userAuthSystem.canChangeAccountsPermission()

    if (showMenu.value) {
        if (canEditPermission) {

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
            if (superAdmin) {

            DropdownMenuItem(onClick = {
                // Show AlertDialog
                showDialog.value = true
            }) {
                Text("مسح", style = CairoTypography.h4)
            }
        }
        }
    }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = {
                Text(text = " ")
            },
            text = {
                Text(alertText,textAlign = TextAlign.End,
                    style = CairoTypography.h3)
            },
            confirmButton = {
                GradientButton(
                    text = "موافق",
                    icon = Icons.Default.ConfirmationNumber,
                    onClick = {
                        onRemove()
                        showDialog.value = false
                    },
                    colors = AppColors.blueGradient, cornerRadius = 30.dp
                )
            },
            dismissButton = {

                GradientButton(
                    text = "الغاء",
                    icon = Icons.Default.Cancel,
                    onClick = {
                        //                    onCancel()
                        showDialog.value = false
                    },
                    colors = AppColors.RedGradient, cornerRadius = 30.dp
                )
            }
        )
    }
}

