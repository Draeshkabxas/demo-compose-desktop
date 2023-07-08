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
import common.component.CustomButton
import common.component.DirectoryDialog
import common.component.HeadLineWithDate
import common.component.Screens

import features.courses.domain.model.Course
import features.courses.presentation.courses.FilterEvent.*
import features.courses.presentation.courses.component.Filters
import features.courses.presentation.courses.component.PaginatedTable
import org.koin.compose.koinInject
import styles.AppColors.blue
import styles.AppColors.green


@Composable
fun CoursesScreen(
    navController: NavController<Screens>,
    viewModel: CoursesScreenViewModel = koinInject()
) {
    val widths = listOf(70.dp, 82.dp, 150.dp, 130.dp, 150.dp, 115.dp, 85.dp, 110.dp, 140.dp, 85.dp,85.dp, 65.dp, 87.dp)
    val headers = listOf(
        "التسلسل", "رقم الملف", "الإسم رباعي", "الرقم الوطني", "إسم الأم", "المؤهل العلمي", "المدينة", "رقم الهاتف",
        "القائم بالتجنيد", "حالة الملف", "النواقص","النتيجة", "إحالة لتدريب"
    )

    var coursesData by remember { mutableStateOf<List<Course>>(emptyList()) }

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
                modifier = Modifier.align(Alignment.TopEnd),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                CustomButton(
                    text = "إضافة ملف", icon = Icons.Default.AddTask,
                    onClick = {
                        navController.navigate(Screens.AddCoursesScreen())
                    },
                    buttonColor = blue
                )
                CustomButton(
                    text = "طباعة",
                    icon = Icons.Default.Print,
                    onClick = {
                        DirectoryDialog(
                            onApproved = { filePath ->
                                viewModel.printToXlsxFile(
                                    filePath,
                                    onError = {},
                                    onLoading = {},
                                    onSuccess = { println("print xlsx is success") }
                                )
                            },
                            onCanceled = {
                                println("on canceled")
                            },
                            onError = {
                                println("on onError")
                            }
                        )
                    },
                    buttonColor = green
                )
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
                            PaginatedTable(headers, coursesData, 13, widths)
                        }
                    }
                }
            }

        }
    }
}



