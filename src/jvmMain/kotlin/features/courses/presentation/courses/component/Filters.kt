package features.courses.presentation.courses.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import common.component.CustomOutlinedTextField

import common.component.*
import styles.AppColors
import styles.AppColors.blue
import utils.Education
import utils.LibyanCities
import utils.getAllAgeGroupArabicNames

@Composable
fun Filters(
    onFilterLibyaId:(String)->Unit,
    onFilterFileNumber:(String)->Unit,
    onFilterEducationLevel:(String)->Unit,
    onFilterCity:(String)->Unit,
    onFilterFileState:(Boolean)->Unit,
    onFilterReferralForTraining:(Boolean)->Unit,
    onFilterAgeGroup: (String) -> Unit,
    onReset:()->Unit,
    onSubmit:()->Unit
){
    var selectedCity by remember { mutableStateOf("إختر المدينة") }
    var selectededucation by remember { mutableStateOf("إختر المؤهل") }
    var selectedFileState by remember { mutableStateOf("إختر حالة الملف") }
    var selectedTrainer by remember { mutableStateOf("إختر  نعم او لا") }
    var selectedAge by remember { mutableStateOf("إختر الفئة العمرية") }
    val libyaIdState = remember { mutableStateOf("") }
    val fileNumberState = remember { mutableStateOf("") }

    var isCancelVisible by remember { mutableStateOf(false) }
    var isMoreFiltersVisible by remember { mutableStateOf(false) }

    val resetFilters = {
        onReset()
        selectedCity = "إختر المدينة"
        selectededucation = "إختر المؤهل"
        selectedFileState = "إختر حالة الملف"
        selectedTrainer = "إختر  نعم او لا"
        selectedAge = "إختر الفئة العمرية"
        libyaIdState.value = ""
        fileNumberState.value = ""
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            CustomOutlinedTextField(
                valueState = libyaIdState,
                hint = "إبحث بالرقم الوطني ",
                isError = false,
                errorMessage = "",
                onValueChange = { onFilterLibyaId(it) },
                onNextChange = { onFilterLibyaId(it) },
                width = 200.dp,
                inputType = InputType.NUMBER,
                maxLength = 12 // Set the maximum length to N characters

            )
            CustomButton(
                text = "إبحث", icon = Icons.Default.Search, onClick = {
                    onSubmit()
                    isCancelVisible = !isCancelVisible
                },
                buttonColor = Color(0xff3B5EA1)
            )
            if (isCancelVisible) {
                Button(
                    shape = RoundedCornerShape(100.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                    modifier = Modifier.padding(horizontal = 0.dp, vertical = 0.dp),

                    onClick = {
                        resetFilters()
                        isCancelVisible = !isCancelVisible

                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )

                }

            }
            CustomButton(
                text = "فلتره البحث ",
                icon = Icons.Default.FilterList,
                onClick = { isMoreFiltersVisible = !isMoreFiltersVisible },

                buttonColor = blue
            )
            Spacer(modifier = Modifier.width(530.dp))

        }
            if (isMoreFiltersVisible) {
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
                            onValueChange = { onFilterFileNumber(it) },
                            onNextChange = { onFilterFileNumber(it) },
                            width = 120.dp,
                            inputType = InputType.NUMBER,
                            maxLength = 5 // Set the maximum length to N characters

                        )
                        SelectorWithLabel(
                            label = "المؤهل العلمي : ",
                            items = Education.values().map { it.arabicName },
                            selectedItem = selectededucation,
                            onItemSelected = { education ->
                                selectededucation = education
                                onFilterEducationLevel(education)
                            }
                        )

                        SelectorWithLabel(
                            label = "المدينة : ",
                            items = LibyanCities.values().map { it.arabicName },
                            selectedItem = selectedCity,
                            onItemSelected = { city ->
                                selectedCity = city
                                onFilterCity(city)
                            }
                        )

                        val fileValues = listOf("مستوفي", "نواقص")
                        SelectorWithLabel(
                            label = "حالة الملف : ",
                            items = fileValues,
                            selectedItem = selectedFileState,
                            onItemSelected = { file ->
                                selectedFileState = file
                                onFilterFileState(
                                    file == fileValues[0]
                                )
                            }
                        )

                        val trainerValues = listOf("نعم", "لا")
                        SelectorWithLabel(
                            label = "إحالة للتدريب : ",
                            items = trainerValues,
                            selectedItem = selectedTrainer,
                            onItemSelected = { trainer ->
                                selectedTrainer = trainer
                                onFilterReferralForTraining(
                                    trainer == trainerValues[0]
                                )
                            }
                        )
                        CustomButton(
                            text = "إبحث", icon = Icons.Default.Search, onClick = {
                                onSubmit()
                            },
                            buttonColor = Color(0xff3B5EA1)
                        )
                        CustomButton(
                            text = "إعادة ضبط", icon = Icons.Default.RestartAlt, onClick = {
                                resetFilters()
                            },
                            buttonColor = Color.Red
                        )



                    }
                     Row(                        modifier = Modifier.fillMaxWidth().sizeIn(maxHeight = 85.dp),
                     ) {
                         SelectorWithLabel(
                             label = " الفئة العمرية : ",
                             items = getAllAgeGroupArabicNames(),
                             selectedItem = selectedAge,
                             onItemSelected = { ageGroup ->
                                 selectedAge = ageGroup
                                 onFilterAgeGroup(ageGroup)
                             }
                         )
                     }

                }
            }
        }
}