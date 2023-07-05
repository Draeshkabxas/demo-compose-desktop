package features.sons_of_officers.presentation.sons_of_officers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.ui.Modifier

import common.component.*
import common.toColor
import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.presentation.sons_of_officers.FilterEvent.*
import org.koin.compose.koinInject
import styles.AppColors
import styles.AppColors.secondary


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
    var libyaIdState = remember { mutableStateOf("") }
    var fileNumberState = remember { mutableStateOf("") }


    val countries = listOf("طرابلس", "تاجوراء", "القاربولي", "الخمس", "زليطن", "مصراته")
    val educations =
        listOf("ماجستير", "بكالوريوس", "ليسنس", "معهد عالي", "معهد متوسط", "شهادة ثانوية", "شهادة اعدادية", "إبتدائية")
    val fileState = listOf("مستوفي", "نواقص")
    val trainerState = listOf("نعم", "لا")

// for row visibility
    var isRowVisible by remember { mutableStateOf(false) }
    var isCancelVisible by remember { mutableStateOf(false) }

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
                    valueState = libyaIdState,
                    hint = "إبحث بالرقم الوطني ",
                    errorMessage = "",
                    onValueChange = { viewModel.onEvent(FilterLibyaId(it)) },
                    onNextChange = { viewModel.onEvent(FilterLibyaId(it)) },
                    width = 200.dp

                )
            CustomButton(
                text = "إبحث", icon = Icons.Default.Search, onClick = {
                    viewModel.onEvent(Submit)
                    isCancelVisible =!isCancelVisible
                },
                buttonColor = Color(0xff3B5EA1)
            )
            if (isCancelVisible) {
                Button(
                    shape = RoundedCornerShape(100.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                    modifier = Modifier.padding(horizontal = 0.dp, vertical = 0.dp),

                            onClick = {
                        viewModel.onEvent(Reset)
                        selectedCity = "إختر المدينة"
                        selectededucation = "إختر المؤهل"
                        selectedFileState = "إختر حالة الملف"
                        selectedTrainer = "إختر  نعم او لا"
                        libyaIdState.value = ""
                        fileNumberState.value = ""
                        isCancelVisible =!isCancelVisible

                    },
                ){
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )

                }

            }
                CustomButton(
                text = "فلتره البحث ", icon = Icons.Default.FilterList, onClick = { isRowVisible = !isRowVisible },

                buttonColor = Color(0xff3B5EA1)
            )
            Spacer(modifier = Modifier.width(530.dp))

            CustomButton(
                text = "إضافة ملف", icon = Icons.Default.AddTask,
                onClick = {
                    navController.navigate(SystemScreen.AddSonsOfOfficersScreen.name)
                },
                buttonColor = Color(0xff3B5EA1)
            )
            CustomButton(
                text = "طباعة",
                icon = Icons.Default.Print,
                onClick = {
                   DirectoryDialog(
                      onApproved = {filePath->
                          viewModel.printToXlsxFile(
                              filePath,
                              onError = {},
                              onLoading = {},
                              onSuccess = { println("print xlsx is success")}
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
                buttonColor = Color(0xff3F6F52)
            )
        }
        if (isRowVisible) {
            Column(
                modifier = Modifier.fillMaxWidth().sizeIn(maxHeight = 240.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().sizeIn(maxHeight = 100.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    CustomOutlinedTextField(
                        valueState = fileNumberState,
                        hint = "إبحث برقم الملف",
                        errorMessage = "",
                        onValueChange = { viewModel.onEvent(FilterFileNumber(it)) },
                        onNextChange = { viewModel.onEvent(FilterFileNumber(it)) },
                        width = 120.dp
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
                    CustomButton(
                        text = "إعادة ضبط", icon = Icons.Default.RestartAlt, onClick = {
                            viewModel.onEvent(Reset)
                            selectedCity = "إختر المدينة"
                            selectededucation = "إختر المؤهل"
                            selectedFileState = "إختر حالة الملف"
                            selectedTrainer = "إختر  نعم او لا"
                            libyaIdState.value = ""
                            fileNumberState.value = ""
                        },
                        buttonColor = Color.Red
                    )


                }


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
                            PaginatedTable(headers,viewModel.peopleData,13,widths)
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


