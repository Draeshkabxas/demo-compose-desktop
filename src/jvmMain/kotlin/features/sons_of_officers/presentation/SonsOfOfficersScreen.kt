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


@Composable
fun SonsOfOfficersScreen(
    navController: NavController
) {
    //   for select input
    var selectedCountry by remember { mutableStateOf("إختر المدينة") }
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
        listOf("1", "John", "Doe", "25", "Male", "USA", "BSc", "New York", "Single", "Yes", "$1000"),
        listOf("2", "Jane", "Doe", "30", "Female", "Canada", "MSc", "Toronto", "Married", "No", "$2000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000"),
        //

        // Add more rows here...
    )

    val headers = listOf(
         "التسلسل",  "رقم الملف", "الإسم رباعي", "الرقم الوطني","إسم الأم",  "المؤهل العلمي", "المدينة", "رقم الهاتف",
        "القائم بالتجنيد" , "إحالة لتدريب","حالة الملف","النواقص"
    )

//    val headers = listOf("Header 1", "Header 2", "Header 3", "Header 4", "Header 5", "Header 6", "Header 7", "Header 8", "Header 9", "Header 10", "Header 11")
//    val data = listOf(
//        ("Cell 1,1", "Cell 1,2", "Cell 1,3", "Cell 1,4", "Cell 1,5", "Cell 1,6", "Cell 1,7", "Cell 1,8", "Cell 1,9", "Cell 1,10", "Cell 1,11"),
//    // add more rows as needed
//    )

//    val headers = listOf("Name", "Email", "Phone", "Active")
//    val data = mutableStateListOf(
//        listOf("John Doe", "john.doe@example.com", "(123) 456-7890", true),
//        listOf("Jane Doe", "jane.doe@example.com", "(234) 567-8901", false),
//        listOf("Bob Smith", "bob.smith@example.com", "(345) 678-9012", false),
//    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,

        ) {
        HeadLineWithDate(text = "منظومة أبناء الضباط ", date = "1/7/2023  1:30:36 PM")
//        Section("البيانات الشخصية",3){
//            repeat(10){
//                item {
////                    CustomOutlinedTextField(errorMessage = "")
//                }
//            }
//        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            CustomOutlinedTextField(hint = "إبحث بالرقم الوطني ", errorMessage = "")
            CustomButton(
                text = "إبحث", icon = Icons.Default.Search, onClick = { /* Do something */ },
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
            modifier = Modifier.fillMaxWidth(),
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
                selectedItem = selectedCountry,
                onItemSelected = { country -> selectedCountry = country }
            )
            SelectorWithLabel(
                label = "حالة الملف : ",
                items = fileState,
                selectedItem = selectedFileState,
                onItemSelected = { file -> selectedFileState = file }
            )
            SelectorWithLabel(
                label = "إحالة للتدريب : ",
                items = trainerState,
                selectedItem = selectedTrainer,
                onItemSelected = { trainer -> selectedTrainer = trainer }
            )
            CustomButton(
                text = "إبحث", icon = Icons.Default.Search, onClick = { /* Do something */ },
                buttonColor = Color(0xff3B5EA1)
            )
        }
    }

//        table
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            LazyColumn(Modifier.padding(16.dp)) {
                item {
                    MaterialTheme {
                        Surface(modifier = Modifier.size(1400.dp)) {
//                        Table(
//                            columns = headers,
//                            rows = data
//                        )
                            Table(
                                modifier = Modifier.fillMaxSize(),
                                headers = headers,
                                data = data,
                                cellContent = { columnIndex, rowIndex ->
                                    Text(
                                        text = data[rowIndex][columnIndex],
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            )
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

    Box(modifier = modifier.then(Modifier.horizontalScroll(horizontalScrollState))) {
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
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            LazyColumn(state = verticalLazyListState) {
                items(count = rowCount, key = { rowIndex -> rowIndex }) { rowIndex ->
                    Row(modifier = rowModifier) {
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

