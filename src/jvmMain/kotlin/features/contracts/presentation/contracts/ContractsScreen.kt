package features.contracts.presentation.contracts


import AlertSystem.presentation.showErrorMessage
import AlertSystem.presentation.showSuccessMessage
import ImportXlsxDialog
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

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import features.contracts.domain.model.Contract
import features.contracts.presentation.contracts.FilterEvent.*
import features.contracts.presentation.contracts.component.Filters
import features.contracts.presentation.contracts.component.PaginatedTable
import features.sons_of_officers.presentation.sons_of_officers.PrintEvent
import org.koin.compose.koinInject
import styles.AppColors.GreenGradient
import styles.AppColors.RedGradient
import styles.AppColors.blueGradient
import styles.CairoTypography
import utils.getUserAuth


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContractsScreen(
    navController: NavController<Screens>,
    viewModel: ContractsScreenViewModel = koinInject()
) {
    val widths =
        listOf(70.dp,40.dp, 82.dp, 280.dp, 120.dp, 180.dp, 115.dp, 115.dp, 150.dp, 110.dp, 120.dp, 140.dp, 160.dp, 300.dp, 200.dp)
    val headers = listOf(
        "تحديد","رقم", "رقم الملف", "الإسم رباعي",
        "الرقم الوطني", "إسم الأم", "جنسية الأم",
        "المؤهل العلمي", "المدينة", "رقم الهاتف",
        "التبعية", "إسم المصرف", "رقم الحساب",
        "الملاحظات", "الاشاري"
    )

    val userAuthSystem = getUserAuth()
    var canEditPermission = userAuthSystem.canEdit()
    var superAdminPermission = userAuthSystem.canChangeAccountsPermission()
    var contractsData by remember { mutableStateOf<List<Contract>>(emptyList()) }
    var showPrintListDialog by remember { mutableStateOf(false) }
    var showPrintDirectoryPathDialog by remember { mutableStateOf(false) }
    val currentDataTablePage = mutableStateOf(0)

    val resetDataTablePage: () -> Unit = { currentDataTablePage.value = 0 }


    LaunchedEffect(key1 = true) {
        viewModel.contractsDataFlow.collect { people ->
            contractsData = people
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HeadLineWithDate(text = "منظومة العقود ", date = "1/7/2023  1:30:36 PM")
        Box {
            Filters(
                onFilterLibyaId = { viewModel.onEvent(FilterLibyaId(it)) },
                onFilterFileNumber = { viewModel.onEvent(FilterFileNumber(it)) },
                onFilterEducationLevel = { viewModel.onEvent(FilterEducationLevel(it)) },
                onFilterCity = { viewModel.onEvent(FilterCity(it)) },
                onFilterMotherName = { viewModel.onEvent(FilterMotherName(it)) },
                onFilterName = { viewModel.onEvent(FilterName(it)) },
                onFilterAgeGroup = { viewModel.onEvent(FilterAgeGroup(it)) },
                onReset = {
                    resetDataTablePage()
                    viewModel.onEvent(Reset)
                },
                onSubmit = {
                    resetDataTablePage()
                    viewModel.onEvent(Submit)
                },
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
                            navController.navigate(Screens.AddContractsScreen())
                        },
                        colors = blueGradient, cornerRadius = 30.dp
                    )
                    var showImportDialog by remember { mutableStateOf(false) }
                    var contracts by remember { mutableStateOf<Map<String, List<Contract>>>(emptyMap()) }
                    if (superAdminPermission) {
                        GradientButton(
                            text = "استيراد عقود من ملف",
                            icon = Icons.Default.FileUpload,
                            onClick = {
                                var xlsxFilePath = ""
                                GetFilePathDialog(
                                    onError = {
                                        it.showErrorMessage()
                                    },
                                    onSuccess = { xlsxFilePath = it }
                                )
                                viewModel.importContracts(
                                    filePath = xlsxFilePath,
                                    onLoading = {},
                                    onError = {
                                        it.showErrorMessage()
                                    },
                                    onSuccess = {
                                        contracts = it
                                        showImportDialog = true
                                    })
                            },
                            colors = blueGradient, cornerRadius = 30.dp
                        )

                    }
                    if (showImportDialog) {
                        var success by remember { mutableStateOf(false) }
                        ImportXlsxDialog(
                            contractsByEducationLevel = contracts,
                            onFilterEducationLevel = { educationLevel, _ ->
                                !viewModel.checkIfEducationLevelIsCorrect(educationLevel)
                            },
                            onDismiss = {
                                showImportDialog = false
                            },
                            onError = {
                                it.showErrorMessage()
                            },
                            onModifyEducationLevel = { contracts, convertMap ->
                                var contractsAfterModified: List<Contract>? = emptyList<Contract>()

                                viewModel.changeContractEducationLevelType(
                                    contracts = contracts,
                                    convertedMap = convertMap,
                                    onError = {
                                        it.showErrorMessage()
                                    },
                                    onSuccess = {
                                        contractsAfterModified = it
                                    }
                                )
                                contractsAfterModified
                            },
                            onAdd = {
                                viewModel.addAllImportedContracts(it,
                                    onLoading = {},
                                    onError = {
                                        it.showErrorMessage()
                                    },
                                    onSuccess = {
                                        success = it
                                        showImportDialog = false
                                        ("تم إضافة العقود من الملف بنجاح").showSuccessMessage()
                                        resetDataTablePage()
                                    })
                                success
                            }
                        )
                    }

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
                                        viewModel.removeAllContracts(
                                            onLoading = {},
                                            onError = {
                                                it.showErrorMessage()
                                            },
                                            onSuccess = {
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
                                "رقم الملف",
                                "الاسم رباعي",
                                "اسم الام",
                                "جنسية الام",
                                "المؤهل العلمي",
                                "المدينة",
                                "رقم الهاتف",
                                "التبعية",
                                "اسم المصرف",
                                "رقم الحساب",
                                "الرقم الوطني",
                                "الرقم الاشاري",
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
                                ("تمت عملية الطباعة بنجاح").showSuccessMessage()

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
                        Surface(modifier = Modifier.height(1600.dp).fillMaxWidth()) {
                            PaginatedTable(
                                navController, headers, contractsData, 20, widths,
                                onRemoveContract = { contract ->
                                    viewModel.removeContract(contract, onSuccess = {})
                                },
                                currentPage = currentDataTablePage,
                                onSelectedListChange = { value ->
                                    println("on Selected $value")
                                    viewModel.checkedPersons.value = value
                                },
                                checkedList = viewModel.checkedPersons.value
                            ) // Pass the currentPage value from ViewModel
                        }
                    }
                }
            }

        }
    }
}


