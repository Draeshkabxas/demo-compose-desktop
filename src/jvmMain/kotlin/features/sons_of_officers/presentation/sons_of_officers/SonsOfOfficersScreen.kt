package features.sons_of_officers.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import navcontroller.NavController
import styles.CairoTypography
import androidx.compose.material.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*

import androidx.compose.ui.Modifier

import androidx.compose.ui.layout.layout
import common.component.*
import common.component.simpledatatable.*
import features.sons_of_officers.presentation.sons_of_officers.FilterEvent.*
import features.sons_of_officers.presentation.sons_of_officers.SonsOfOfficersScreenViewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import java.awt.Desktop
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import java.lang.reflect.Method
import javax.swing.JFileChooser


@Composable
fun SonsOfOfficersScreen(
    navController: NavController,
    viewModel: SonsOfOfficersScreenViewModel = koinInject()
) {
    //   for select input
    var selectedCity by remember { mutableStateOf("إختر المدينة") }
    var selectededucation by remember { mutableStateOf("إختر المؤهل") }
    var selectedFileState by remember { mutableStateOf("إختر حالة الملف") }
    var selectedTrainer by remember { mutableStateOf("إختر  نعم او لا") }

    val countries = listOf("طرابلس", "تاجوراء", "القاربولي", "الخمس", "زليطن", "مصراته")
    val educations =
        listOf("ماجستير", "بكالوريوس", "ليسنس", "معهد عالي", "معهد متوسط", "شهادة ثانوية", "شهادة اعدادية", "إبتدائية")
    val fileState = listOf("مستوفي", "نواقص")
    val trainerState = listOf("نعم", "لا")

// for row visibility
    var isRowVisible by remember { mutableStateOf(false) }
//    for table
    val data = listOf(
        listOf("1", "222", " احمد محمد احمد محمود", "1199911111111", "عائشة محمد عبدالله", "شهادة اعدادية", "طرابلس", "0910000000", "احمد محمد احمد", "لا", "نواقص","إضافة"),
        listOf("2", "Jane", "Doe", "30", "Female", "Canada", "MSc", "Toronto", "Married", "No", "$2000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("4", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("5", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("6", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("7", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("8", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("9", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("10", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("11", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("12", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("13", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("14", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("15", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("16", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("17", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("18", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("19", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("20", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("21", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("22", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("23", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("24", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("25", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("26", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("27", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        //

        // Add more rows here...
    )
  val widths = listOf(70.dp, 82.dp,200.dp, 130.dp, 150.dp, 115.dp,100.dp, 110.dp, 140.dp, 87.dp, 85.dp, 80.dp)

    val headers = listOf(
         "التسلسل",  "رقم الملف", "الإسم رباعي", "الرقم الوطني","إسم الأم",  "المؤهل العلمي", "المدينة", "رقم الهاتف",
        "القائم بالتجنيد" , "إحالة لتدريب","حالة الملف","النواقص"
    )

    Column(
        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.Start,
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Top

    ) {
        HeadLineWithDate(text = "منظومة أبناء الضباط ", date = "1/7/2023  1:30:36 PM")
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            CustomOutlinedTextField(
                hint = "إبحث بالرقم الوطني ",
                errorMessage = "",
                onValueChange = { viewModel.onEvent(FilterLibyaId(it)) },
                onNextChange = { viewModel.onEvent(FilterLibyaId(it)) },
            )
            CustomButton(
                text = "إبحث", icon = Icons.Default.Search, onClick = {
                    viewModel.onEvent(Submit)
                },
                buttonColor = Color(0xff3B5EA1)
            )
            CustomButton(
                text = "فلتره", icon = Icons.Default.FilterList, onClick = { isRowVisible = !isRowVisible },

                buttonColor = Color(0xff3B5EA1)
            )
            Spacer(modifier = Modifier.width(570.dp))

            CustomButton(
                text = "إضافة ملف", icon = Icons.Default.AddTask,
                onClick = {
                    navController.navigate(SystemScreen.AddSonsOfOfficersScreen.name)
                },
                buttonColor = Color(0xff3B5EA1)
            )
            val scope = rememberCoroutineScope()
            CustomButton(
                text = "طباعة",
                icon = Icons.Default.Print,
                onClick = {
                    scope.launch {
                        val fileDialog = FileDialog(Frame(), "Choose a file", FileDialog.LOAD)
                        fileDialog.isVisible = true
                        val file = File(fileDialog.directory, fileDialog.file)
                        println("File path: ${file.absolutePath}")                    }
                },
                buttonColor = Color(0xff3F6F52)
            )
        }
        if (isRowVisible) {
        Row(
            modifier = Modifier.fillMaxWidth().sizeIn(maxHeight = 100.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            CustomOutlinedTextField(hint = "إبحث برقم الملف", errorMessage = "")
            SelectorWithLabel(
                label = "المؤهل العلمي : ",
                items = educations,
                selectedItem = selectededucation,
                onItemSelected = { education -> selectededucation = education }
            )

                SelectorWithLabel(
                    label = "المدينة : ",
                    items = countries,
                    selectedItem = selectedCity,
                    onItemSelected = { city ->
                        selectedCity = city
                        viewModel.onEvent(FilterCity(city))
                    }
                )
                SelectorWithLabel(
                    label = "حالة الملف : ",
                    items = fileState,
                    selectedItem = selectedFileState,
                    onItemSelected = { file ->
                        selectedFileState = file
                        viewModel.onEvent(
                            FilterFileState(
                                file == fileState[0]
                            )
                        )
                    }
                )
                SelectorWithLabel(
                    label = "إحالة للتدريب : ",
                    items = trainerState,
                    selectedItem = selectedTrainer,
                    onItemSelected = { trainer ->
                        selectedTrainer = trainer
                        viewModel.onEvent(
                            FilterReferralForTraining(
                                trainer == trainerState[0]
                            )
                        )
                    }
                )
                CustomButton(
                    text = "إبحث", icon = Icons.Default.Search, onClick = {
                        viewModel.onEvent(Submit)
                    },
                    buttonColor = Color(0xff3B5EA1)
                )
            }
        }

//        table
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
        ) {
            LazyColumn(Modifier.padding(10.dp)) {
                item {
                    MaterialTheme {
                        Surface(modifier = Modifier.size(1400.dp)) {
//                        Table(
//                            columns = headers,
//                            rows = data
//                        )
//                            Table(
//                                modifier = Modifier.fillMaxSize(),
//                                headers = headers,
//                                data = data,
//                                cellContent = { columnIndex, rowIndex ->
//                                    Text(
//                                        text = data[rowIndex][columnIndex],
//                                        style = MaterialTheme.typography.body1,
//                                        modifier = Modifier.padding(8.dp)
//                                    )
//                                }
//                            )
                            PaginatedTable(headers,data,13,widths)
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


