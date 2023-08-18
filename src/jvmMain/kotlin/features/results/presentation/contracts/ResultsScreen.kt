package features.results.presentation.results


import AlertSystem.presentation.showErrorMessage
import AlertSystem.presentation.showSuccessMessage
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import navcontroller.NavController
import androidx.compose.material.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import common.component.*


import features.results.domain.model.Results
import features.results.presentation.results.component.Filters
import features.results.presentation.results.component.PaginatedTable
import org.koin.compose.koinInject
import styles.AppColors.GreenGradient
import styles.AppColors.RedGradient
import styles.AppColors.blueGradient
import styles.CairoTypography
import utils.getUserAuth


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ResultsScreen(
    navController: NavController<Screens>,
    viewModel: ResultsScreenViewModel = koinInject()
) {
    val widths =
        listOf(100.dp, 450.dp, 160.dp, 150.dp, 160.dp, 400.dp)
    val headers = listOf(
        "التسلسل", "الإسم رباعي",
        "رقم الهاتف", "نتائج التحاليل", "تاريخ التحاليل",
        "الملاحظات"
    )
    val userAuthSystem = getUserAuth()
    var canEditPermission = userAuthSystem.canEdit()
    var superAdminPermission = userAuthSystem.canChangeAccountsPermission()
    var resultsData by remember { mutableStateOf<List<Results>>(emptyList()) }
    var showPrintListDialog by remember { mutableStateOf(false) }
    var showPrintDirectoryPathDialog by remember { mutableStateOf(false) }
    val currentDataTablePage = mutableStateOf(0)

    val resetDataTablePage: () -> Unit = { currentDataTablePage.value = 0 }

    LaunchedEffect(key1 = true) {
        viewModel.resultsDataFlow.collect { people ->
            resultsData = people
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HeadLineWithDate(text = "منظومة نتائج التحاليل ", date = "1/7/2023  1:30:36 PM")
        Box {
            Filters(
                onFilterDate = { viewModel.onEvent(FilterEvent.FilterDate(it)) },
                onFilterName = { viewModel.onEvent(FilterEvent.FilterName(it)) },
                onReset = {
                    resetDataTablePage()
                    viewModel.onEvent(FilterEvent.Reset)
                },
                onSubmit = {
                    resetDataTablePage()
                    viewModel.onEvent(FilterEvent.Submit)
                },
            )
            Row(
                modifier = Modifier.align(Alignment.TopEnd).padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                if (canEditPermission) {
                    GradientButton(
                        text = "إضافة نتيجة",
                        icon = Icons.Default.AddTask,
                        onClick = {
                            navController.navigate(Screens.AddResultsScreen())
                        },
                        colors = blueGradient, cornerRadius = 30.dp
                    )
                    GradientButton(
                        text = "استيراد النتائج من ملف",
                        icon = Icons.Default.FileUpload,
                        onClick = {
                            var filePath = ""
                            GetFilePathDialog(
                                onError = {},
                                onSuccess = {
                                    filePath = it
                                },
                            )
                            if (filePath.isNotEmpty()) {
                                viewModel.importResults(
                                    filePath = filePath,
                                    onLoading = {},
                                    onError = {
                                        ("الملف الذي قمت بتحميله تالف الرجاء اختيار ملف اخر").showErrorMessage()

                                    },
                                    onSuccess = {
                                        viewModel.addAllImportedResults(
                                            results = it,
                                            onLoading = {},
                                            onError = {},
                                            onSuccess = {
                                                resetDataTablePage()
                                                ("تم إضاقة نتائج التحاليل من الملف بنجاح").showSuccessMessage()
                                            }
                                        )
                                    }
                                )
                            }
                        },
                        colors = blueGradient, cornerRadius = 30.dp
                    )
                    GradientButton(
                        text = "طباعة",
                        icon = Icons.Default.Print,
                        onClick = {
                            showPrintListDialog = true
                        },

                        colors = GreenGradient,
                        cornerRadius = 30.dp
                    )
                    var showDialogDelet by remember { mutableStateOf(false) }

                    if (showDialogDelet) {
                        AlertDialog(
                            onDismissRequest = { showDialogDelet = false },
                            title = { Text(" ", textAlign = TextAlign.Start, style = CairoTypography.h3) },
                            text = {
                                Text(
                                    "هل أنت متأكد من أنك تريد مسح كافة البيانات ف المنظومة ؟",
                                    textAlign = TextAlign.End,
                                    style = CairoTypography.h3
                                )
                            },
                            confirmButton = {
                                GradientButton(
                                    text = "مسح",
                                    icon = Icons.Default.DeleteForever,
                                    onClick = {
                                        showDialogDelet = false
                                        viewModel.removeAllResults(onLoading = {}, onError = {
                                            it.showErrorMessage()
                                        }, onSuccess = {
                                            "تمت عملية المسح بنجاح".showSuccessMessage()

                                        })
                                    },
                                    colors = RedGradient, cornerRadius = 30.dp
                                )
                            },
                            dismissButton = {

                                GradientButton(
                                    text = "الغاء",
                                    icon = Icons.Default.Cancel,
                                    onClick = { showDialogDelet = false },

                                    colors = RedGradient, cornerRadius = 30.dp
                                )
                            }
                        )
                    }
                    if (superAdminPermission) {
                        GradientButton(
                            text = "مسح الكل",
                            icon = Icons.Default.DeleteForever,
                            onClick = {
                                showDialogDelet = true
                            },
                            colors = RedGradient, cornerRadius = 30.dp
                        )
                    }
                    if (showPrintListDialog) {
                        PrintDialog(
                            columns = listOf(
                                "الاسم رباعي",
                                "رقم الهاتف",
                                "نتائج التحاليل",
                                "تاريخ التحاليل",
                                "الملاحظات"
                            ),
                            onPrintColumnsChanged = {
                                viewModel.onPrintEvent(PrintEvent.PrintList(it))
                                showPrintDirectoryPathDialog = true
                            },
                            onDismiss = { showPrintListDialog = false }
                        )
                    }

                    if (showPrintDirectoryPathDialog) {
                        DirectoryDialog(
                            onApproved = { filePath ->
                                viewModel.onPrintEvent(PrintEvent.PrintToDirectory(filePath))
                                viewModel.onPrintEvent(PrintEvent.Submit)
                                showPrintDirectoryPathDialog = false
                                ("تمت عملية الطباعة بنجاح" ).showSuccessMessage()
                            },
                            onCanceled = {
                                showPrintDirectoryPathDialog = false
                                ("لم يتم الحصول على مسار الذي تريد الطباعة فيه").showErrorMessage()
                            },
                            onError = {
                                ("لم يتم الحصول على مسار الذي تريد الطباعة فيه").showErrorMessage()
                            }
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
        ) {
            LazyColumn(Modifier.padding(5.dp)) {
                item {
                    MaterialTheme {
                        Surface(modifier = Modifier.size(1400.dp)) {
                            PaginatedTable(navController, headers, resultsData, 25, widths,
                                onRemoveResults = { results ->
                                    viewModel.removeResults(results, onSuccess = {})
                                })
                        }
                    }
                }
            }

        }
    }
}


