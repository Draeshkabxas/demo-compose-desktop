package features.sons_of_officers.presentation.sons_of_officers

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import navcontroller.NavController
import androidx.compose.material.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*

import androidx.compose.ui.Modifier

import androidx.compose.ui.layout.layout
import common.component.*
import common.component.simpledatatable.*
import features.sons_of_officers.presentation.sons_of_officers.FilterEvent.*
import org.koin.compose.koinInject


@Composable
fun SonsOfOfficersScreen(
    navController: NavController,
    viewModel: SonsOfOfficersScreenViewModel= koinInject()
) {
    //   for select input
    var selectedCity by remember { mutableStateOf("إختر المدينة") }
    var selectededucation by remember { mutableStateOf("إختر المؤهل") }
    var selectedFileState by remember { mutableStateOf("إختر حالة الملف") }
    var selectedTrainer by remember { mutableStateOf("إختر  نعم او لا") }

    val countries = listOf("طرابلس", "تاجوراء", "القاربولي", "الخمس", "زليطن", "مصراته")
    val educations = listOf("ماجستير","بكالوريوس", "ليسنس", "معهد عالي", "معهد متوسط", "شهادة ثانوية", "شهادة اعدادية", "إبتدائية")
    val fileState = listOf("مستوفي", "نواقص" )
    val trainerState = listOf("نعم", "لا" )

// for row visibility
    var isRowVisible by remember { mutableStateOf(false) }
//    for table
    val data = listOf(
        listOf("1", "222", " احمد محمد احمد محمود", "1199911111111", "عائشة محمد عبدالله", "بكالوريوس", "طرابلس", "0910000000", "احمد محمد احمد", "لا", "غير مستوفي","إضافة"),
        listOf("2", "Jane", "Doe", "30", "Female", "Canada", "MSc", "Toronto", "Married", "No", "$2000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000","ediet"),
        //

        // Add more rows here...
    )

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
                onValueChange = { viewModel.onEvent(FilterLibyaId(it))},
                onNextChange = { viewModel.onEvent(FilterLibyaId(it))},
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
            CustomButton(
                text = "طباعة", icon = Icons.Default.Print, onClick = { /* Do something */ },
                buttonColor = Color(0xff3F6F52)
            )
        }
        if (isRowVisible) {
        Row(
            modifier = Modifier.fillMaxWidth().sizeIn(maxHeight = 100.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            CustomOutlinedTextField(
                hint = "إبحث برقم الملف",
                errorMessage = "",
                onValueChange = { viewModel.onEvent(FilterFileNumber(it))},
                onNextChange = { viewModel.onEvent(FilterFileNumber(it))},
            )
            SelectorWithLabel(
                label = "المؤهل العلمي : ",
                items = educations,
                selectedItem = selectededucation,
                onItemSelected = { education ->
                    selectededucation = education
                    viewModel.onEvent(FilterEducationLevel(education))
                }
            )

            SelectorWithLabel(
                label = "المدينة : ",
                items = countries,
                selectedItem = selectedCity,
                onItemSelected = { city -> selectedCity = city
                    viewModel.onEvent(FilterCity(city))
                }
            )
            SelectorWithLabel(
                label = "حالة الملف : ",
                items = fileState,
                selectedItem = selectedFileState,
                onItemSelected = { file ->
                    selectedFileState = file
                    viewModel.onEvent(FilterFileState(
                        file == fileState[0]
                    ))
                }
            )
            SelectorWithLabel(
                label = "إحالة للتدريب : ",
                items = trainerState,
                selectedItem = selectedTrainer,
                onItemSelected = { trainer ->
                    selectedTrainer = trainer
                    viewModel.onEvent(FilterReferralForTraining(
                        trainer == trainerState[0]
                    )) }
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
        SimpleDataTableScreenPreview()
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
@Composable
fun Table(
    modifier: Modifier = Modifier,
    rowModifier: Modifier = Modifier,
    verticalLazyListState: LazyListState = rememberLazyListState(),
    horizontalScrollState: ScrollState = rememberScrollState(),
    headers: List<String>,
    data: List<List<String>>,
    beforeRow: (@Composable (rowIndex: Int) -> Unit)? = null,
    afterRow: (@Composable (rowIndex: Int) -> Unit)? = null,
    cellContent: @Composable (columnIndex: Int, rowIndex: Int) -> Unit
) {
    val rowCount = data.size
    val columnCount = headers.size
    val columnWidths = remember { mutableStateMapOf<Int, Int>() }

    // Add a border to the table
    Box(
        modifier = modifier.border(1.dp, Color(0xff3B5EA1))
            .then(Modifier.horizontalScroll(horizontalScrollState))
    ) {
        Column {
            Row(modifier = rowModifier) {
                headers.forEachIndexed { columnIndex, header ->
                    Box(
                        modifier = Modifier
                            .layout { measurable, constraints ->
                                val placeable = measurable.measure(constraints)

                                val existingWidth = columnWidths[columnIndex] ?: 0
                                val maxWidth = maxOf(existingWidth, placeable.width)

                                if (maxWidth > existingWidth) {
                                    columnWidths[columnIndex] = maxWidth
                                }

                                layout(width = maxWidth, height = placeable.height) {
                                    placeable.placeRelative(0, 0)
                                }
                            }
                            .background(Color(0xff3B5EA1))
                    ) {
                        Text(
                            text = header,
                            style = MaterialTheme.typography.h5,
                            color = Color.White,
                            modifier = Modifier.padding(13.dp)
                        )
                    }
                }
            }

            LazyColumn(state = verticalLazyListState) {
                items(count = rowCount, key = { rowIndex -> rowIndex }) { rowIndex ->
                    // Add a border to every row
                    Row(
                        modifier = rowModifier.border(
                            1.dp, Color(0xff3B5EA1)
                        ).background(Color.White),
                    ) {
                        data[rowIndex].forEachIndexed { columnIndex, cellData ->
                            Box(
                                modifier = Modifier
                                    .layout { measurable, constraints ->
                                        val placeable = measurable.measure(constraints)

                                        val existingWidth = columnWidths[columnIndex] ?: 0
                                        val maxWidth = maxOf(existingWidth, placeable.width)

                                        if (maxWidth > existingWidth) {
                                            columnWidths[columnIndex] = maxWidth
                                        }

                                        layout(width = maxWidth, height = placeable.height) {
                                            placeable.placeRelative(0, 0)
                                        }
                                    }
                            ) {
                                cellContent(columnIndex, rowIndex)
                            }
                        }
                    }
                }
            }
        }
    }
}

