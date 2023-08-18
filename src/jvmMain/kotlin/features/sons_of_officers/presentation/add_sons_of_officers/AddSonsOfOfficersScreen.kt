package features.sons_of_officers.presentation.add_sons_of_officers

import AlertSystem.presentation.showSuccessMessage
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import common.component.*
import common.component.ScreenMode.EDIT
import features.courses.presentation.add_courses.CourseInfoFormEvent
import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.presentation.add_sons_of_officers.AddSonsOfOfficersViewModel.ValidationEvent
import navcontroller.NavController
import features.sons_of_officers.presentation.add_sons_of_officers.PersonalInfoFormEvent.*
import org.koin.compose.koinInject
import styles.AppColors.blue
import styles.AppColors.blueGradient
import styles.CairoTypography
import utils.Education
import utils.LibyanCities

@Composable
fun AddSonsOfOfficersScreen(
    navController: NavController<Screens>,
    person: Person? = null,
    mode:ScreenMode,
    viewModel: AddSonsOfOfficersViewModel = koinInject()
) {

    LaunchedEffect(key1 = true) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                ValidationEvent.Success -> {
                    if(mode == EDIT){
                        "تمت عملية التعديل بنجاح".showSuccessMessage()
                    }else{
                        "تمت عملية الاضافة بنجاح".showSuccessMessage()
                    }
                    navController.navigateReplacement(Screens.SonsOfOfficersScreen())
                }
                else -> {}
            }
        }
    }
    var selectedEducation by remember { mutableStateOf("إختر المؤهل") }
    var selectedCity by remember { mutableStateOf("إختر المدينة") }
    var isFirstRender by remember { mutableStateOf(true) }
    if (isFirstRender){
        viewModel.setupMode(mode,person)
        selectedEducation = viewModel.state.educationLevel
        selectedCity = viewModel.state.city
        isFirstRender = false
    }
    val state = viewModel.state

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        HeadLineWithDate(text = "منظومة أبناء الضباط / إضافة طالب ", date ="1/7/2023  1:30:36 PM" )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
        ) {
            LazyColumn(Modifier.padding(10.dp)) {

                item {
                    MaterialTheme {
                        Surface(modifier = Modifier.height(800.dp)) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                            Section("البيانات الشخصية",4){
                        val personalInputsName= viewModel.personalInputsNameAndValue
                        item {
                            CustomOutlinedTextField(
                                value = state.name,
                                onValueChange = { viewModel.onEvent(NameChanged(it)) },
                                onNextChange = { viewModel.onEvent(NameChanged(it)) },
                                isError = state.nameError!=null,
                                hint = personalInputsName[0],
                                errorMessage = state.nameError.toString(),
                                inputType = InputType.TEXT,
                                width =50.dp,
                                maxLength = 30 // Set the maximum length to N characters

                            )
//                        Spacer(modifier = Modifier.width(10.dp))
                        }
                        item {
                            CustomOutlinedTextField(
                                value = state.motherName,
                                onValueChange = { viewModel.onEvent(MotherNameChanged(it)) },
                                onNextChange = { viewModel.onEvent(MotherNameChanged(it)) },
                                hint = personalInputsName[1],
                                isError = state.motherNameError!=null,
                                errorMessage = state.motherNameError.toString(),
                                inputType = InputType.TEXT,
                                maxLength = 23 // Set the maximum length to N characters

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
                                maxLength = 6 // Set the maximum length to N characters


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
                                inputType = InputType.TEXT,
                                maxLength = 20 // Set the maximum length to N characters
                            )
                        }
                        item {
                            Column {
                                SelectorWithLabel(
                                    label = "المؤهل العلمي : ",
                                    items = Education.values().map { it.arabicName },
                                    selectedItem = if (selectedEducation.isEmpty()) "إختر المؤهل " else selectedEducation ,
                                    onItemSelected = {
                                        selectedEducation = it
                                        viewModel.onEvent(EducationLevelChanged(it))

                                    }

                                )

                                if (state.educationLevelError != null)
                                    Text(state.educationLevelError.toString()
                                        ,color = Color.Red,
                                        style = CairoTypography.body2)
                            }
                        }

                        item{
                            Column {
                                SelectorWithLabel(
                                    label = "المدينة : ",
                                    items = LibyanCities.values().map { it.arabicName },
                                    selectedItem = if (selectedCity.isEmpty()) "إختر المدينة " else selectedCity ,
                                    onItemSelected = {
                                        selectedCity = it
                                        viewModel.onEvent(CityChanged(it)) }
                                )
                                if (state.cityError!=null)
                                    Text(state.cityError.toString(),
                                        color = Color.Red,
                                        style = CairoTypography.body2
                                    )
                            }
                        }
                        item {
                            CustomOutlinedTextField(
                                value = state.commission,
                                onValueChange = { viewModel.onEvent(CommissionChanged(it)) },
                                onNextChange = { viewModel.onEvent(CommissionChanged(it)) },
                                hint = personalInputsName[6],
                                isError = state.commissionError!=null,
                                errorMessage = state.commissionError.toString(),
                                inputType = InputType.All,
                                        maxLength = 8 // Set the maximum length to N characters
                            )
                        }
                                item {
                                    CustomOutlinedTextField(
                                        value = state.notes,
                                        onValueChange = { viewModel.onEvent(NotesChanged(it)) },
                                        onNextChange = { viewModel.onEvent(NotesChanged(it)) },
                                        hint = personalInputsName[7],
                                        isError = state.notesError!=null,
                                        errorMessage = state.notesError.toString(),
                                        inputType = InputType.All,
                                                maxLength = 25 // Set the maximum length to N characters

                                    )
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
                        colors = blueGradient,
                    )
                }

    }
}}}}}}
