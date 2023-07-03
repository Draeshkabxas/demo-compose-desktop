package features.sons_of_officers.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import authorization.presentation.register.RegisterViewModel
import common.component.*
import features.sons_of_officers.presentation.AddSonsOfOfficersViewModel.ValidationEvent
import navcontroller.NavController
import features.sons_of_officers.presentation.PersonalInfoFormEvent.*
import org.koin.compose.koinInject
import styles.CairoTypography

@Composable
fun AddSonsOfOfficersScreen(
    navController: NavController,
    viewModel: AddSonsOfOfficersViewModel = koinInject()
) {
    LaunchedEffect(key1 = true) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                ValidationEvent.Success -> {
                    navController.navigateBack()
                }
                else -> {}
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var selectededucation by remember { mutableStateOf("إختر المؤهل") }
        var selectedCity by remember { mutableStateOf("إختر المدينة") }


        HeadLineWithDate(text = "منظومة أبناء الضباط / إضافة طالب ", date ="1/7/2023  1:30:36 PM" )
        Section("البيانات الشخصية",3){
            val personalInputsName= viewModel.personalInputsNameAndValue
            val state = viewModel.state
                    item {
                        CustomOutlinedTextField(
                            onValueChange = { viewModel.onEvent(NameChanged(it)) },
                            onNextChange = { viewModel.onEvent(NameChanged(it)) },
                            isError = state.nameError!=null,
                            hint = personalInputsName[0],
                            errorMessage = state.nameError.toString()
                        )
                    }
            item {
                CustomOutlinedTextField(
                    onValueChange = { viewModel.onEvent(MotherNameChanged(it)) },
                    onNextChange = { viewModel.onEvent(MotherNameChanged(it)) },
                    hint = personalInputsName[1],
                    isError = state.motherNameError!=null,
                    errorMessage = state.motherNameError.toString()
                )
            }
            item {
                CustomOutlinedTextField(
                    onValueChange = { viewModel.onEvent(FileNumberChanged(it)) },
                    onNextChange = { viewModel.onEvent(FileNumberChanged(it)) },
                    hint = personalInputsName[2],
                    isError = state.fileNumberError!=null,
                    errorMessage = state.fileNumberError.toString()
                )
            }
            item {
                CustomOutlinedTextField(
                    onValueChange = { viewModel.onEvent(LibyaIdChanged(it)) },
                    onNextChange = { viewModel.onEvent(LibyaIdChanged(it)) },
                    hint = personalInputsName[3],
                    isError = state.libyaidError!=null,
                    errorMessage = state.libyaidError.toString()
                )
            }
            item {
                CustomOutlinedTextField(
                    onValueChange = { viewModel.onEvent(PhoneNumberChanged(it)) },
                    onNextChange = { viewModel.onEvent(PhoneNumberChanged(it)) },
                    hint = personalInputsName[4],
                    isError = state.phoneNumberError!=null,
                    errorMessage = state.phoneNumberError.toString()
                )
            }
            item {
                CustomOutlinedTextField(
                    onValueChange = { viewModel.onEvent(RecruiterChanged(it)) },
                    onNextChange = { viewModel.onEvent(RecruiterChanged(it)) },
                    hint = personalInputsName[6],
                    isError = state.recruiterError!=null,
                    errorMessage = state.recruiterError.toString()
                )
            }
            val educationLevel = listOf("ماجستير","بكالوريوس", "ليسنس", "معهد عالي", "معهد متوسط", "شهادة ثانوية", "شهادة اعدادية", "إبتدائية")
            item {
                Column {
//                    RoundedDropdownMenu(
//                        items = educationLevel,
//                        onSelectItem = { viewModel.onEvent(EducationLevelChanged(it))},
//                        label = { Text("المؤهل العلمي") }
//                    ){
//                        Text(it)
//                    }
                    SelectorWithLabel(
                        label = "المؤهل العلمي : ",
                        items = educationLevel,
                        selectedItem = selectededucation,
                        onItemSelected = { viewModel.onEvent(EducationLevelChanged(it))}
                    )

                    if (state.educationLevelError != null)
                      Text(state.educationLevelError.toString()
                      ,color = Color.Red,
                          style = CairoTypography.body2)
                }
            }

            val  cities= listOf("طرابلس", "تاجوراء", "القاربولي", "الخمس", "زليطن", "مصراته")
            item{
                Column {
//                    RoundedDropdownMenu(
//                        items = cities,
//                        onSelectItem = { viewModel.onEvent(CityChanged(it)) },
//                        label = { Text("المدينة") }
//                    ) {
//                        Text(it)
//                    }
                    SelectorWithLabel(
                        label = "المدينة : ",
                        items = cities,
                        selectedItem = selectedCity,
                        onItemSelected = { viewModel.onEvent(CityChanged(it)) }
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
        Section("المصوغات المطلوبة",3){
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
        Section("الاجراءات",3){
            viewModel.proceduresInputNameAndValues.forEach { name,valueState->
                item{
                    CheckBoxWithLabel(
                        name,
                        valueState.value,
                        onCheckedChange = {valueState.value = it}
                    )
                }
            }
        }

                CustomButton(
                    text = "حفظ",
                    icon = Icons.Default.Save,
                    onClick = {  viewModel.onEvent(Submit) },
                    buttonColor = Color(0xff3B5EA1)
                )
    }
}
