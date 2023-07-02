package features.sons_of_officers.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import authorization.presentation.register.RegisterViewModel
import common.component.*
import features.sons_of_officers.presentation.AddSonsOfOfficersViewModel.ValidationEvent
import navcontroller.NavController
import features.sons_of_officers.presentation.PersonalInfoFormEvent.*
import org.koin.compose.koinInject

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
            val educationLevel = listOf("بكالوريس","ماجستير","دبلوم عالي")
            item {
                Column {
                    RoundedDropdownMenu(
                        items = educationLevel,
                        onSelectItem = { viewModel.onEvent(EducationLevelChanged(it))},
                        label = { Text("المؤهل العلمي") }
                    ){
                        Text(it)
                    }
                    if (state.educationLevelError != null)
                      Text(state.educationLevelError.toString())
                }
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
            val  cities= listOf("tripoli","masrath","sabratha","regdalin")
            item{
                Column {
                    RoundedDropdownMenu(
                        items = cities,
                        onSelectItem = { viewModel.onEvent(CityChanged(it)) },
                        label = { Text("المدينة") }
                    ) {
                        Text(it)
                    }
                    if (state.cityError!=null)
                    Text(state.cityError.toString())
                }
            }
        }
        Section("المسوغات المطلوبة",3){
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
        RoundedButton(
            text ="حفظ",
            onClick = {
                viewModel.onEvent(Submit)
            }
        )
    }
}
