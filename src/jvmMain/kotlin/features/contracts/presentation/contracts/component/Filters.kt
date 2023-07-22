package features.contracts.presentation.contracts.component

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.dp
import common.component.CustomOutlinedTextField

import common.component.*
import styles.AppColors.RedGradient
import styles.AppColors.blueGradient
import utils.Education
import utils.LibyanCities
import utils.getAllAgeGroupArabicNames

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Filters(
    onFilterLibyaId: (String) -> Unit,
    onFilterFileNumber: (String) -> Unit,
    onFilterEducationLevel: (String) -> Unit,
    onFilterCity: (String) -> Unit,
    onFilterMotherName: (String) -> Unit,
    onFilterName: (String) -> Unit,
    onFilterAgeGroup: (String) -> Unit,
    onReset: () -> Unit,
    onSubmit: () -> Unit
) {
    var selectedCity by remember { mutableStateOf("إختر المدينة") }
    var selectededucation by remember { mutableStateOf("إختر المؤهل العلمي") }
    var selectedAge by remember { mutableStateOf("إختر الفئة العمرية") }
    val libyaIdState = remember { mutableStateOf("") }
    val fileNumberState = remember { mutableStateOf("") }
    val motherNameState = remember { mutableStateOf("") }
    val personNameState = remember { mutableStateOf("") }


    var isCancelVisible by remember { mutableStateOf(false) }
    var isMoreFiltersVisible by remember { mutableStateOf(false) }

    val resetFilters = {
        onReset()
        selectedCity = "إختر المدينة"
        selectededucation = " إختر المؤهل العلمي"
        selectedAge = "إختر الفئة العمرية"
        libyaIdState.value = ""
        motherNameState.value = ""
        personNameState.value = ""
        fileNumberState.value = ""
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
                valueState = libyaIdState,
                onValueChange = { onFilterLibyaId(it) },
                onNextChange = {onFilterLibyaId(it)},
                hint = "إبحث بالرقم الوطني",
                isError = false,
                errorMessage = "",
                width = 200.dp,
                inputType = InputType.NUMBER,
                maxLength = 12 // Set the maximum length to N characters
            )
            GradientButton(
                text = "إبحث",
                icon = Icons.Default.Search,
                onClick = { onSubmit()
                    isCancelVisible = !isCancelVisible
                },
                cornerRadius = 30.dp,
                colors = blueGradient

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
                cornerRadius = 30.dp,
                colors = blueGradient
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
                    modifier = Modifier.fillMaxWidth().sizeIn(maxHeight = 100.dp).padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    CustomOutlinedTextField(
                        valueState = fileNumberState,
                        hint = "إبحث برقم الملف",
                        errorMessage = "",
                        onValueChange = { onFilterFileNumber(it) },
                        onNextChange = { onFilterFileNumber(it) },
                        width = 140.dp,
                        inputType = InputType.NUMBER,
                        maxLength = 5 // Set the maximum length to N characters


                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    CustomOutlinedTextField(
                        valueState = personNameState,
                        hint = "إبحث بالإسم",
                        errorMessage = "",
                        onValueChange = { onFilterName(it) },
                        onNextChange = { onFilterName(it) },
                        width = 190.dp,
                        inputType = InputType.TEXT

                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    CustomOutlinedTextField(
                        valueState = motherNameState,
                        hint = "إبحث بإسم الأم",
                        errorMessage = "",
                        onValueChange = { onFilterMotherName(it) },
                        onNextChange = { onFilterMotherName(it) },
                        width = 190.dp,
                        inputType = InputType.TEXT

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
                    SelectorWithLabel(
                        label = "",
                        items = getAllAgeGroupArabicNames(),
                        selectedItem = selectedAge,
                        onItemSelected = { ageGroup ->
                            selectedAge = ageGroup
                            onFilterAgeGroup(ageGroup)
                        }
                    )

                    GradientButton(
                        text = "إبحث",
                        icon = Icons.Default.Search,
                        onClick = {
                            onSubmit()
                        },
                        colors =  blueGradient
                        ,cornerRadius = 30.dp,


                    )
                    GradientButton(
                        text = "إعادة ضبط",
                        icon = Icons.Default.RestartAlt,
                        onClick = {
                            resetFilters()
                        },
                        colors = RedGradient
                                ,cornerRadius = 30.dp
                    )
                }
            }
        }
    }
}