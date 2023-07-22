package features.courses.presentation.courses


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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import common.component.*

import features.courses.domain.model.Course
import features.courses.presentation.courses.FilterEvent.*
import features.courses.presentation.courses.FilterEvent.Submit
import features.courses.presentation.courses.PrintEvent.*
import features.courses.presentation.courses.component.Filters
import features.courses.presentation.courses.component.PaginatedTable
import org.koin.compose.koinInject
import styles.AppColors
import styles.AppColors.GreenGradient
import styles.AppColors.blue
import styles.AppColors.blueGradient
import styles.AppColors.green
import styles.CairoTypography
import utils.getUserAuth


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CoursesScreen(
    navController: NavController<Screens>,
    viewModel: CoursesScreenViewModel = koinInject()
) {
    val widths = listOf(70.dp, 82.dp, 150.dp, 130.dp, 150.dp, 115.dp, 85.dp, 110.dp, 140.dp, 85.dp, 85.dp, 65.dp, 95.dp)
    val headers = listOf(
        "التسلسل", "رقم الملف", "الإسم رباعي", "الرقم الوطني", "إسم الأم", "المؤهل العلمي", "المدينة", "رقم الهاتف",
        "القائم بالتجنيد", "حالة الملف", "النواقص", "النتيجة", "إحالة لتدريب"
    )
    val userAuthSystem = getUserAuth()
    var canEditPermission = userAuthSystem.canEdit()
    var superAdminPermission = userAuthSystem.canChangeAccountsPermission()

    var coursesData by remember { mutableStateOf<List<Course>>(emptyList()) }
    var showPrintListDialog by remember { mutableStateOf(false) }
    var showPrintDirectoryPathDialog by remember { mutableStateOf(false) }



    LaunchedEffect(key1 = true) {
        viewModel.coursesDataFlow.collect { courses ->
            coursesData = courses
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HeadLineWithDate(text = "منظومة الدورات ", date = "1/7/2023  1:30:36 PM")
        Box {
            Filters(

                onFilterLibyaId = { viewModel.onEvent(FilterLibyaId(it)) },
                onFilterFileNumber = { viewModel.onEvent(FilterFileNumber(it)) },
                onFilterEducationLevel = { viewModel.onEvent(FilterEducationLevel(it)) },
                onFilterCity = { viewModel.onEvent(FilterCity(it)) },
                onFilterFileState = { viewModel.onEvent(FilterFileState(it)) },
                onFilterReferralForTraining = { viewModel.onEvent(FilterReferralForTraining(it)) },
                onFilterAgeGroup = { viewModel.onEvent(FilterAgeGroup(it)) },
                onFilterHealthStatus = { viewModel.onEvent(FilterHealthStatus(it)) },
                onReset = { viewModel.onEvent(Reset) },
                onSubmit = { viewModel.onEvent(Submit) },
            )
            Row(
                modifier = Modifier.align(Alignment.TopEnd).padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                if(canEditPermission){
                GradientButton(
                    text = "إضافة ملف",
                    icon = Icons.Default.AddTask,
                    onClick = {
                        navController.navigate(Screens.AddCoursesScreen())
                    },
                    colors =  blueGradient,
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
                    var showDialogDelet by remember { mutableStateOf(false) }

                    if (showDialogDelet) {
                        AlertDialog(
                            onDismissRequest = { showDialogDelet = false },
                            title = { Text(" ", textAlign = TextAlign.Start, style = CairoTypography.h3) },
                            text = { Text("هل أنت متأكد من أنك تريد مسح كافة البيانات ف المنظومة ؟", textAlign = TextAlign.End, style = CairoTypography.h3) },
                            confirmButton = {
                                GradientButton(
                                    text = "مسح",
                                    icon = Icons.Default.DeleteForever,
                                    onClick = {
                                        showDialogDelet = false
                                        viewModel.removeAllCourses(onLoading = {}, onError = {}, onSuccess = {})
                                    },
                                    colors =   AppColors.RedGradient, cornerRadius = 30.dp
                                )
                            },
                            dismissButton = {

                                GradientButton(
                                    text = "الغاء",
                                    icon = Icons.Default.Cancel,
                                    onClick = { showDialogDelet = false },

                                    colors = AppColors.RedGradient, cornerRadius = 30.dp
                                )
                            }
                        )
                    }
                    if (superAdminPermission){
                        GradientButton(
                            text = "مسح الكل",
                            icon = Icons.Default.DeleteForever,
                            onClick = {
                                showDialogDelet = true
                            },
                            colors = AppColors.RedGradient, cornerRadius = 30.dp
                        )
                    }
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
                            PaginatedTable(navController, headers, coursesData, 13, widths)
                        }
                    }
                }
            }

        }
    }
}



