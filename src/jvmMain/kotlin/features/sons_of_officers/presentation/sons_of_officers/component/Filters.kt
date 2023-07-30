package features.sons_of_officers.presentation.sons_of_officers.component

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
import styles.AppColors.RedGradient
import styles.AppColors.blue
import styles.AppColors.blueGradient
import utils.*
import utils.HealthStatus.All

@Composable
fun Filters(
    onFilterName: (String) -> Unit,
    onFilterLibyaId:(String)->Unit,
    onFilterFileNumber:(String)->Unit,
    onFilterEducationLevel:(String)->Unit,
    onFilterCity:(String)->Unit,
    onFilterFileState:(Boolean)->Unit,
    onFilterReferralForTraining:(Boolean)->Unit,
    onFilterAgeGroup: (String) -> Unit,
    onFilterHealthStatus:(HealthStatus)->Unit,
    onReset:()->Unit,
    onSubmit:()->Unit
){
    val personNameState = remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf("إختر المدينة") }
    var selectedHealthStatus by remember { mutableStateOf("إختر الحالة الصحية") }
    var selectededucation by remember { mutableStateOf("إختر المؤهل العلمي") }
    var selectedFileState by remember { mutableStateOf("إختر حالة الملف") }
    var selectedTrainer by remember { mutableStateOf("إحالة لتدريب") }
    var selectedAge by remember { mutableStateOf("إختر الفئة العمرية") }
    val libyaIdState = remember { mutableStateOf("") }
    val fileNumberState = remember { mutableStateOf("") }

    var isCancelVisible by remember { mutableStateOf(false) }
    var isMoreFiltersVisible by remember { mutableStateOf(false) }

    val resetFilters = {
        onReset()
        selectedHealthStatus ="إختار الحالة الصحية"
        selectedCity = "إختر المدينة"
        selectededucation = "إختر المؤهل العلمي"
        selectedFileState = "إختر حالة الملف"
        selectedTrainer = "إحالة لتدريب"
        selectedAge = "إختر الفئة العمرية"
        libyaIdState.value = ""
        fileNumberState.value = ""
        personNameState.value = ""
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            CustomOutlinedTextField(
                valueState = personNameState,
                hint = "إبحث بالإسم",
                errorMessage = "",
                onValueChange = { onFilterName(it) },
                onNextChange = { onFilterName(it) },
                width = 190.dp,
                inputType = InputType.TEXT

            )
            GradientButton(
                text = "إبحث",
                icon = Icons.Default.Search,
                onClick = { onSubmit()
                    isCancelVisible = !isCancelVisible
                },
                colors =blueGradient,
                cornerRadius = 30.dp
            )
            if (isCancelVisible) {
                IconButton(
                    imageVector = Icons.Default.Cancel,
                    onClick = {
                        resetFilters()
                        isCancelVisible = !isCancelVisible
                    },
                    shape = RoundedCornerShape(100.dp),
                    colors =Color.Red,
                    contentPadding= PaddingValues(0.dp)
                )

            }
            GradientButton(
                text = "فلتره البحث ",
                icon = Icons.Default.FilterList,
                onClick = { isMoreFiltersVisible = !isMoreFiltersVisible },
                colors = blueGradient,
                cornerRadius = 30.dp
            )
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(
                Icons.Default.RestartAlt,
                onClick = { onSubmit() },
                shape = RoundedCornerShape(100.dp),
                colors =Color.Red,
                contentPadding= PaddingValues(0.dp)
            )
            Spacer(modifier = Modifier.width(530.dp))

        }
        if (isMoreFiltersVisible) {
            Column(
                modifier = Modifier.fillMaxWidth().sizeIn(maxHeight = 240.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().sizeIn(maxHeight = 100.dp).padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    CustomOutlinedTextField(
                        valueState = libyaIdState,
                        hint = "الرقم الوطني ",
                        isError = false,
                        errorMessage = "",
                        onValueChange = { onFilterLibyaId(it) },
                        onNextChange = { onFilterLibyaId(it) },
                        width = 125.dp,
                        inputType = InputType.NUMBER,
                        maxLength = 12 // Set the maximum length to N characters

                    )
                    Spacer(modifier = Modifier.size(width = 4.dp, height = 0.dp))
                    CustomOutlinedTextField(
                        valueState = fileNumberState,
                        hint = "رقم الملف",
                        errorMessage = "",
                        onValueChange = { onFilterFileNumber(it) },
                        onNextChange = { onFilterFileNumber(it) },
                        width = 100.dp,
                        inputType = InputType.All,
                        maxLength = 8 // Set the maximum length to N characters

                    )
                    SelectorWithLabel(
                        label = "",
                        items = Education.values().map { it.arabicName },
                        selectedItem = selectededucation,
                        onItemSelected = { education ->
                            selectededucation = education
                            onFilterEducationLevel(education)
                        }
                    )

                    SelectorWithLabel(
                        label = "",
                        items = LibyanCities.values().map { it.arabicName },
                        selectedItem = selectedCity,
                        onItemSelected = { city ->
                            selectedCity = city
                            onFilterCity(city)
                        }
                    )

                    val fileValues = listOf("مستوفي", "نواقص")
                    SelectorWithLabel(
                        label = "",
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
                        label = "",
                        items = trainerValues,
                        selectedItem = selectedTrainer,
                        onItemSelected = { trainer ->
                            selectedTrainer = trainer
                            onFilterReferralForTraining(
                                trainer == trainerValues[0]
                            )
                        }
                    )
                    SelectorWithLabel(
                        label = "",
                        items = getAllAgeGroupArabicNames(),
                        selectedItem = selectedAge,
                        onItemSelected = { ageGroup ->
                            selectedAge = ageGroup
                            onFilterAgeGroup(ageGroup)
                        }
                    )
                    SelectorWithLabel(
                        label = "",
                        items = HealthStatus.values().filterNot { it == All }.map { it.arabicName },
                        selectedItem = selectedHealthStatus,
                        onItemSelected = { file ->
                            selectedHealthStatus = file
                            onFilterHealthStatus(getHealthStatusFromArabicName(file))
                        }
                    )
                    GradientButton(
                        text = "إبحث",
                        icon = Icons.Default.Search,
                        onClick = {
                            onSubmit()
                        },
                        colors =  blueGradient,
                        cornerRadius = 30.dp
                    )
                    GradientButton(
                        text = "إعادة ضبط",
                        icon = Icons.Default.RestartAlt,
                        onClick = {
                            resetFilters()
                        },
                        colors =  RedGradient
                        ,cornerRadius = 30.dp
                    )
                }
            }
        }
    }
}