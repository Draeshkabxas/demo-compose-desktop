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

import common.component.*
import features.courses.presentation.courses.FilterEvent
import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.presentation.sons_of_officers.FilterEvent.*
import features.sons_of_officers.presentation.sons_of_officers.component.Filters
import org.koin.compose.koinInject
import styles.AppColors.blue
import styles.AppColors.green


@Composable
fun SonsOfOfficersScreen(
    navController: NavController<Screens>,
    viewModel: SonsOfOfficersScreenViewModel = koinInject()
) {
    val widths = listOf(70.dp, 82.dp, 150.dp, 130.dp, 150.dp, 115.dp, 85.dp, 110.dp, 140.dp, 85.dp,85.dp, 65.dp, 87.dp)
    val headers = listOf(
        "التسلسل", "رقم الملف", "الإسم رباعي", "الرقم الوطني", "إسم الأم", "المؤهل العلمي", "المدينة", "رقم الهاتف",
        "القائم بالتجنيد", "حالة الملف", "النواقص","النتيجة", "إحالة لتدريب"
    )

    var peopleData by remember { mutableStateOf<List<Person>>(emptyList()) }

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
            features.courses.presentation.courses.component.Filters(
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
                modifier = Modifier.align(Alignment.TopEnd),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                CustomButton(
                    text = "إضافة ملف", icon = Icons.Default.AddTask,
                    onClick = {
                        navController.navigate(Screens.AddSonsOfOfficersScreen(mode = ScreenMode.EDIT))
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
                            PaginatedTable(headers, peopleData, 13, widths)
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


