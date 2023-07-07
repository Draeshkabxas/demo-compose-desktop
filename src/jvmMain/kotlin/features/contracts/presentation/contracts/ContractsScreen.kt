package features.contracts.presentation.contracts


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
import features.contracts.domain.model.Contract
import features.contracts.presentation.contracts.FilterEvent.*
import features.contracts.presentation.contracts.component.Filters
import features.contracts.presentation.contracts.component.PaginatedTable
import org.koin.compose.koinInject
import styles.AppColors.blue
import styles.AppColors.green


@Composable
fun ContractsScreen(
    navController: NavController,
    viewModel: ContractsScreenViewModel = koinInject()
) {
    val widths = listOf(70.dp, 82.dp, 150.dp, 130.dp, 130.dp,90.dp, 115.dp, 100.dp, 110.dp, 100.dp, 105.dp, 90.dp, 85.dp)
    val headers = listOf(
        "التسلسل", "رقم الملف", "الإسم رباعي", "الرقم الوطني", "إسم الأم","جنسية الأم", "المؤهل العلمي", "المدينة", "رقم الهاتف",
        "التبعية", "إسم المصرف", "رقم الحساب", "الملاحظات"
    )

    var contractsData by remember { mutableStateOf<List<Contract>>(emptyList()) }

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
                        navController.navigate(SystemScreen.AddContractsScreen.name)
                    },
                    buttonColor = blue
                )
                CustomButton(
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
//                            PaginatedTable(headers, contractsData, 13, widths)
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


