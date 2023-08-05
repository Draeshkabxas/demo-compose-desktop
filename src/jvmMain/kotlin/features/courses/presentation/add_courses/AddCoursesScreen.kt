package features.courses.presentation.add_courses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import common.component.*
import features.courses.domain.model.Course
import features.courses.presentation.add_courses.AddCourseViewModel.ValidationEvent
import features.courses.presentation.add_courses.CourseInfoFormEvent.*
import features.sons_of_officers.presentation.add_sons_of_officers.PersonalInfoFormEvent
import navcontroller.NavController
import org.koin.compose.koinInject
import styles.AppColors.blue
import styles.AppColors.blueGradient
import styles.CairoTypography
import utils.Education
import utils.LibyanCities

@Composable
fun AddCoursesScreen(
    navController: NavController<Screens>,
    course: Course? = null,
    mode:ScreenMode,
    viewModel: AddCourseViewModel = koinInject()
) {
    LaunchedEffect(key1 = true) {
        viewModel.validationEvents.collect { event ->
            println("event : $event")
            when (event) {
                ValidationEvent.Success -> {
                    println("Success event : $event")
                    navController.navigateReplacement(Screens.CoursesScreen())
                }
                else -> {}
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var selectedEducation by remember { mutableStateOf("إختر المؤهل") }
        var selectedCity by remember { mutableStateOf("إختر المدينة") }
        var isFirstRender by remember { mutableStateOf(true) }
        if (isFirstRender){
            viewModel.setupMode(mode,course)
            selectedEducation = viewModel.state.educationLevel
            selectedCity = viewModel.state.city
            isFirstRender = false
        }
        HeadLineWithDate(text = "منظومة الدورات / إضافة طالب ", date ="1/7/2023  1:30:36 PM" )
        Section("البيانات الشخصية",4){
            val personalInputsName= viewModel.courseInputsNameAndValue
            val state = viewModel.state
            item {
                CustomOutlinedTextField(
                    value = state.name,
                    onValueChange = { viewModel.onEvent(NameChanged(it)) },
                    onNextChange = { viewModel.onEvent(NameChanged(it)) },
                    isError = state.nameError!=null,
                    hint = personalInputsName[0],
                    errorMessage = state.nameError.toString(),
                    inputType = InputType.TEXT

                )
            }
            item {
                CustomOutlinedTextField(
                    value = state.motherName,
                    onValueChange = { viewModel.onEvent(MotherNameChanged(it)) },
                    onNextChange = { viewModel.onEvent(MotherNameChanged(it)) },
                    hint = personalInputsName[1],
                    isError = state.motherNameError!=null,
                    errorMessage = state.motherNameError.toString(),
                    inputType = InputType.TEXT

                )
            }
            item {
                CustomOutlinedTextField(
                    value = state.fileNumber,
                    onValueChange = { viewModel.onEvent(FileNumberChanged(it)) },
                    onNextChange = { viewModel.onEvent(FileNumberChanged(it)) },
                    hint = personalInputsName[2],
                    isError = state.fileNumberError!=null,
                    errorMessage = state.fileNumberError.toString(),
                    inputType = InputType.All,
                    maxLength = 8 // Set the maximum length to N characters


                )
            }
            item {
                CustomOutlinedTextField(
                    value = state.libyaId,
                    onValueChange = { viewModel.onEvent(LibyaIdChanged(it)) },
                    onNextChange = { viewModel.onEvent(LibyaIdChanged(it)) },
                    hint = personalInputsName[3],
                    isError = state.libyaIdError!=null,
                    errorMessage = state.libyaIdError.toString(),
                    inputType = InputType.NUMBER,
                    maxLength = 12 // Set the maximum length to N characters
                )
            }
            item {
                CustomOutlinedTextField(
                    value = state.phoneNumber,
                    onValueChange = { viewModel.onEvent(PhoneNumberChanged(it)) },
                    onNextChange = { viewModel.onEvent(PhoneNumberChanged(it)) },
                    hint = personalInputsName[4],
                    isError = state.phoneNumberError!=null,
                    errorMessage = state.phoneNumberError.toString(),
                    inputType = InputType.NUMBER,
                    maxLength = 10 // Set the maximum length to N characters

                )
            }
            item {
                CustomOutlinedTextField(
                    value = state.recruiter,
                    onValueChange = { viewModel.onEvent(RecruiterChanged(it)) },
                    onNextChange = { viewModel.onEvent(RecruiterChanged(it)) },
                    hint = personalInputsName[5],
                    isError = state.recruiterError!=null,
                    errorMessage = state.recruiterError.toString(),
                    inputType = InputType.TEXT

                )
            }
            item {
                Column {
                    SelectorWithLabel(
                        label = "المؤهل العلمي : ",
                        items = Education.values().map { it.arabicName },
                        selectedItem = if (selectedEducation.isEmpty()) "إختر المؤهل " else selectedEducation ,
                        onItemSelected = {selectedEducation = it
                            viewModel.onEvent(EducationLevelChanged(it))}
                    )

                    if (state.educationLevelError != null)
                        Text(state.educationLevelError.toString()
                            ,color = Color.Red,
                            style = CairoTypography.body2)
                }
            }

            item{
                Column(verticalArrangement = Arrangement.Center) {
                    SelectorWithLabel(
                        label = "المدينة : ",
                        items = LibyanCities.values().map { it.arabicName },

                        selectedItem = if (selectedCity.isEmpty()) "إختر المدينة " else selectedCity ,
                        onItemSelected = {
                            selectedCity = it
                            viewModel.onEvent(CityChanged(it))
                        }
                    )
                    if (state.cityError!=null)
                        Text(state.cityError.toString()
                            ,
                            color = Color.Red,
                            style = CairoTypography.body2
                        )
                }
            }
        }
        Section("المصوغات المطلوبة",4){
            viewModel.justificationsRequiredInputsNameAndValue.forEach{ name,valueState ->
                item{
                    CheckBoxWithLabel(
                        name,
                        valueState.value,
                        onCheckedChange = {valueState.value = it}
                    )
                }
            }
        }
        Section("الاجراءات",4){
            viewModel.proceduresInputNameAndValues.forEach { (name, valueState) ->
                item{
                    CheckBoxWithLabel(
                        name,
                        valueState.value,
                        onCheckedChange = {valueState.value = it}
                    )
                }
            }
        }

        GradientButton(
            text = "حفظ",
            icon = Icons.Default.Save,
            onClick = {  viewModel.onEvent(Submit(mode)) },
            colors = blueGradient
        )
    }
}
