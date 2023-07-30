package features.sons_of_officers.presentation.sons_of_officers

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
import features.courses.presentation.courses.FilterEvent
import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.presentation.sons_of_officers.PrintEvent.*
import features.sons_of_officers.presentation.sons_of_officers.component.Filters
import org.koin.compose.koinInject
import styles.AppColors
import styles.AppColors.GreenGradient
import styles.AppColors.RedGradient
import styles.AppColors.blueGradient
import styles.CairoTypography
import utils.getUserAuth


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SonsOfOfficersScreen(
    navController: NavController<Screens>,
    viewModel: SonsOfOfficersScreenViewModel = koinInject()
) {
    val widths = listOf(70.dp, 82.dp, 150.dp, 130.dp, 150.dp, 115.dp, 85.dp, 110.dp, 140.dp, 85.dp, 85.dp, 65.dp, 95.dp)
    val headers = listOf(
        "التسلسل", "رقم الملف", "الإسم رباعي", "الرقم الوطني", "إسم الأم", "المؤهل العلمي", "المدينة", "رقم الهاتف",
        "القائم بالتجنيد", "حالة الملف", "النواقص", "النتيجة", "إحالة لتدريب"
    )
    val userAuthSystem = getUserAuth()
    var canEditPermission = userAuthSystem.canEdit()
    var superAdminPermission = userAuthSystem.canChangeAccountsPermission()

    var peopleData by remember { mutableStateOf<List<Person>>(emptyList()) }
    var showPrintListDialog by remember { mutableStateOf(false) }
    var showPrintDirectoryPathDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.peopleDataFlow.collect { people ->
            peopleData = people
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HeadLineWithDate(text = "منظومة أبناء الضباط ", date = "1/7/2023  1:30:36 PM")
        Box {
            Filters(
                onFilterName = { viewModel.onEvent(FilterEvent.FilterName(it)) },
                onFilterLibyaId = { viewModel.onEvent(FilterEvent.FilterLibyaId(it)) },
                onFilterFileNumber = { viewModel.onEvent(FilterEvent.FilterFileNumber(it)) },
                onFilterEducationLevel = { viewModel.onEvent(FilterEvent.FilterEducationLevel(it)) },
                onFilterCity = { viewModel.onEvent(FilterEvent.FilterCity(it)) },
                onFilterFileState = { viewModel.onEvent(FilterEvent.FilterFileState(it)) },
                onFilterReferralForTraining = { viewModel.onEvent(FilterEvent.FilterReferralForTraining(it)) },
                onFilterAgeGroup = { viewModel.onEvent(FilterEvent.FilterAgeGroup(it)) },
                onFilterHealthStatus = { viewModel.onEvent(FilterEvent.FilterHealthStatus(it)) },
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
                            navController.navigate(Screens.AddSonsOfOfficersScreen())
                        },
                        colors = blueGradient,
                        cornerRadius = 30.dp
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
                    var showDialogDelete by remember { mutableStateOf(false) }

                    if (showDialogDelete) {
                        AlertDialog(
                            onDismissRequest = { showDialogDelete = false },
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
                                        showDialogDelete = false
                                        viewModel.removeAllPeople(onLoading = {}, onError = {}, onSuccess = {})
                                    },
                                    colors = RedGradient, cornerRadius = 30.dp
                                )
                            },
                            dismissButton = {

                                GradientButton(
                                    text = "الغاء",
                                    icon = Icons.Default.Cancel,
                                    onClick = { showDialogDelete = false },

                                    colors = AppColors.RedGradient, cornerRadius = 30.dp
                                )
                            }
                        )
                    }
                    if (superAdminPermission) {
                        GradientButton(
                            text = "مسح الكل",
                            icon = Icons.Default.DeleteForever,
                            onClick = {
                                showDialogDelete = true
                            },
                            colors = AppColors.RedGradient, cornerRadius = 30.dp
                        )
                    }
                    // Show the print dialog if the boolean flag is true
                    if (showPrintListDialog) {
                        PrintDialog(
                            columns = listOf(
                                "رقم الملف",
                                "الاسم رباعي",
                                "الرقم الوطني",
                                "اسم الام",
                                "المؤهل العلمي",
                                "المدينة",
                                "رقم الهاتف",
                                "القائم بالتجنيد",
                                "النتيجة"
                            ),
                            onPrintColumnsChanged = {
                                viewModel.onPrintEvent(PrintList(it))
                                showPrintDirectoryPathDialog = true
                            },
                            onDismiss = { showPrintListDialog = false }
                        )
                    }

                    if (showPrintDirectoryPathDialog) {
                        DirectoryDialog(
                            onApproved = { filePath ->
                                viewModel.onPrintEvent(PrintToDirectory(filePath))
                                viewModel.onPrintEvent(Submit)
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
//        table

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
        ) {
            LazyColumn(Modifier.padding(10.dp)) {
                item {
                    MaterialTheme {
                        Surface(modifier = Modifier.size(1400.dp)) {
                            PaginatedTable(navController, headers, peopleData, 13, widths,
                                onRemovePerson = {person ->  viewModel.removePerson(person, onSuccess = {})})
                        }
                    }
                }
            }

        }
    }
}
//@Composable
//fun Table() {
//    val data = listOf(
//        listOf("Name", "Age", "Gender"),
//        listOf("John", "28", "Male"),
//        listOf("Jane", "32", "Female"),
//        listOf("Bob", "45", "Male")
//    )
//
//    LazyColumn(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        items(data.size) { index ->
//            LazyRow(
//                modifier = Modifier.fillParentMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                data[index].forEach { cell ->
//                    Text(
//                        text = cell,
//                        modifier = Modifier.padding(16.dp)
//                    )
//                }
//            }
//        }
//    }
//}


