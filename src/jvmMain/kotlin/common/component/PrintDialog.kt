package common.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Print
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import styles.AppColors
import styles.CairoTypography
import kotlin.math.ceil


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PrintDialog(
    size: DpSize = DpSize(400.dp, 500.dp),
    columns: List<String>,
    onPrintColumnsChanged: (List<String>) -> Unit,
    onDismiss: () -> Unit,
) {
    var selectedColumns by remember { mutableStateOf(columns) }

    AlertDialog(
        modifier = Modifier.size(size),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.Transparent,
        onDismissRequest = onDismiss,
        buttons = {
            Card(
                modifier = Modifier.size(size),
                shape = RoundedCornerShape(20.dp),
                elevation = 5.dp,
                backgroundColor = AppColors.white,
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    println("print dialog show")
                    Text(
                        text = "إختر الأعمده التي تريد طباعتها :",
                        style = CairoTypography.h2,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 16.dp),
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val rows = ceil(columns.size.toFloat() / 2).toInt()
                        for (rowIndex in 0 until rows) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                for (i in 0..1) {
                                    val index = rowIndex * 2 + i
                                    if (index < columns.size) {
                                        val column = columns[index]

                                        Row(
                                            modifier = Modifier,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Checkbox(
                                                colors = CheckboxDefaults.colors(
                                                    checkedColor = AppColors.blue,
                                                    uncheckedColor = Color.Gray,
                                                    checkmarkColor = Color.White,
                                                    disabledColor = Color.LightGray,
                                                ),
                                                checked = selectedColumns.contains(column),
                                                onCheckedChange = {
                                                    selectedColumns = if (it) {
                                                        selectedColumns + column
                                                    } else {
                                                        selectedColumns - column
                                                    }
                                                },                                            )
                                            Text(
                                                column,
                                                style = CairoTypography.h4,
                                                modifier = Modifier.padding(horizontal = 8.dp),
                                            )
                                        }
                                    } else {
                                        Spacer(modifier = Modifier.width(20.dp))
                                    }
                                }
                            }
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        GradientButton(
                            text = "طباعة",
                            icon = Icons.Default.Print,
                            onClick = {

                                //                        DirectoryDialog(
//                            onApproved = { filePath ->
//                                viewModel.printToXlsxFile(
//                                    filePath,
//                                    onError = {},
//                                    onLoading = {},
//                                    onSuccess = { println("print xlsx is success") }
//                                )
//                            },
//                            onCanceled = {
//                                println("on canceled")
//                            },
//                            onError = {
//                                println("on onError")
//                            }
//                        )
                            },

                            AppColors.GreenGradient,
                            cornerRadius = 30.dp
                        )

                        GradientButton(
                            text = "إلغاء",
                            icon = Icons.Default.Cancel,
                            onClick = onDismiss,
                            AppColors.RedGradient
                            ,cornerRadius = 30.dp
                        )

                    }
                }
            }
        }
    )
}

@Composable
fun CheckboxWithLabel(
    label: @Composable() () -> Unit,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(end = 8.dp),
        )
        label()
    }
}
