package features.results.presentation.results


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
        listOf(100.dp, 250.dp, 160.dp, 150.dp, 160.dp, 120.dp, 250.dp)
    val headers = listOf(
        "التسلسل" ,"الإسم رباعي",
        "نتائج التحاليل", "تاريخ التحاليل", "رقم الهاتف",
        "تعديل", "الملاحظات"
    )
    val userAuthSystem = getUserAuth()
    var canEditPermission = userAuthSystem.canEdit()
    var superAdminPermission = userAuthSystem.canChangeAccountsPermission()
    var resultsData by remember { mutableStateOf<List<Results>>(emptyList()) }
    var showPrintListDialog by remember { mutableStateOf(false) }
    var showPrintDirectoryPathDialog by remember { mutableStateOf(false) }


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
//                onFilterLibyaId = { viewModel.onEvent(FilterLibyaId(it)) },
//                onFilterFileNumber = { viewModel.onEvent(FilterFileNumber(it)) },
//                onFilterEducationLevel = { viewModel.onEvent(FilterEducationLevel(it)) },
                onFilterDate = { viewModel.onEvent(FilterEvent.FilterDate(it)) },
//                onFilterMotherName = { viewModel.onEvent(FilterMotherName(it)) },
                onFilterName = { viewModel.onEvent(FilterEvent.FilterName(it)) },
//                onFilterAgeGroup = { viewModel.onEvent(FilterAgeGroup(it)) },
                onReset = { viewModel.onEvent(FilterEvent.Reset) },
                onSubmit = { viewModel.onEvent(FilterEvent.Submit) },
            )
            Row(
                modifier = Modifier.align(Alignment.TopEnd).padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                if (canEditPermission) {
                    GradientButton(
                        text = "إضافة ملف",
                        icon = Icons.Default.AddTask,
                        onClick = {
                            navController.navigate(Screens.AddResultsScreen())
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
                                        viewModel.removeAllResults(onLoading = {}, onError = {}, onSuccess = {})
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
                                "رقم الملف",
                                "الاسم رباعي",
                                "اسم الام",
                                "جنسية الام",
                                "المؤهل العلمي",
                                "المدينة",
                                "رقم الهاتف",
                                " التبعية",
                                "اسم المصرف",
                                "رقم الحساب",
                                "الرقم الوطني"
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
                            },
                            onCanceled = {
                                showPrintDirectoryPathDialog = false
                                println("on canceled")
                            },
                            onError = {
                                println("on onError")
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
                            PaginatedTable(navController, headers, resultsData, 13, widths,
                                onRemoveResults  = { results ->
                                    viewModel.removeResults(results, onSuccess = {})
                                })
                        }
                    }
                }
            }

        }
    }
}


