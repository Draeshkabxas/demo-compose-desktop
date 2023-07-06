package features.contracts.presentation.add_contracts

import features.sons_of_officers.presentation.add_sons_of_officers.AddSonsOfOfficersViewModel


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import common.component.*
import features.sons_of_officers.presentation.add_sons_of_officers.AddSonsOfOfficersViewModel.ValidationEvent
import navcontroller.NavController
import features.sons_of_officers.presentation.add_sons_of_officers.PersonalInfoFormEvent.*
import org.koin.compose.koinInject
import styles.AppColors
import styles.AppColors.blue
import styles.CairoTypography

@Composable
fun AddContractsScreen(
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
    val personalInputsNameAndValue = listOf(
        "الاسم رباعي",
        "اسم الام",
        "جنسية الأم",
        "رقم الملف",
        "الرقم الوطني",
        "رقم الهاتف",
        "المؤهل العلمي",
        "التبعية",
        "إسم المصرف",
        "رقم الحساب",
        "ملاحظات"
        )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var selectededucation by remember { mutableStateOf("إختر المؤهل") }
        var selectedCity by remember { mutableStateOf("إختر المدينة") }


        HeadLineWithDate(text = "منظومة العقود / إضافة ملف ", date ="1/7/2023  1:30:36 PM" )
        Section("البيانات الشخصية",
            3){
            val personalInputsName= viewModel.personalInputsNameAndValue
            val state = viewModel.state
            item {
                CustomOutlinedTextField(
                    onValueChange = { viewModel.onEvent(NameChanged(it)) },
                    onNextChange = { viewModel.onEvent(NameChanged(it)) },
                    isError = state.nameError!=null,
                    hint = personalInputsNameAndValue[0],
                    errorMessage = state.nameError.toString(),
                    width = 150.dp,
                    inputType = InputType.TEXT
                )
            }
            item {
                CustomOutlinedTextField(
                    onValueChange = { viewModel.onEvent(MotherNameChanged(it)) },
                    onNextChange = { viewModel.onEvent(MotherNameChanged(it)) },
                    hint = personalInputsNameAndValue[1],
                    isError = state.motherNameError!=null,
                    errorMessage = state.motherNameError.toString(),
                    width = 150.dp,
                    inputType = InputType.TEXT

                )
            }
            item {
                CustomOutlinedTextField(
                    onValueChange = { viewModel.onEvent(FileNumberChanged(it)) },
                    onNextChange = { viewModel.onEvent(FileNumberChanged(it)) },
                    hint = personalInputsNameAndValue[2],
                    isError = state.fileNumberError!=null,
                    errorMessage = state.fileNumberError.toString(),
                    width = 150.dp,
                    inputType = InputType.TEXT
                )
            }
            item {
                CustomOutlinedTextField(
                    onValueChange = { viewModel.onEvent(LibyaIdChanged(it)) },
                    onNextChange = { viewModel.onEvent(LibyaIdChanged(it)) },
                    hint = personalInputsNameAndValue[3],
                    isError = state.libyaidError!=null,
                    errorMessage = state.libyaidError.toString(),
                    width = 150.dp,
                    inputType = InputType.NUMBER,
                    maxLength = 5 // Set the maximum length to N characters

                )
            }
            item {
                CustomOutlinedTextField(
                    onValueChange = { viewModel.onEvent(PhoneNumberChanged(it)) },
                    onNextChange = { viewModel.onEvent(PhoneNumberChanged(it)) },
                    hint = personalInputsNameAndValue[4],
                    isError = state.phoneNumberError!=null,
                    errorMessage = state.phoneNumberError.toString(),
                    width = 150.dp,
                    inputType = InputType.NUMBER,
                    maxLength = 12 // Set the maximum length to N characters


                )
            }
            item {
                CustomOutlinedTextField(
                    onValueChange = { viewModel.onEvent(RecruiterChanged(it)) },
                    onNextChange = { viewModel.onEvent(RecruiterChanged(it)) },
                    hint = personalInputsNameAndValue[5],
                    isError = state.recruiterError!=null,
                    errorMessage = state.recruiterError.toString(),
                    width = 150.dp,
                    inputType = InputType.NUMBER,
                    maxLength = 10 // Set the maximum length to N characters

                )
            }
            item {
                CustomOutlinedTextField(
                    onValueChange = { viewModel.onEvent(RecruiterChanged(it)) },
                    onNextChange = { viewModel.onEvent(RecruiterChanged(it)) },
                    hint = personalInputsNameAndValue[7],
                    isError = state.recruiterError!=null,
                    errorMessage = state.recruiterError.toString(),
                    width = 150.dp,
                    inputType = InputType.TEXT
                )
            }
            item {
                CustomOutlinedTextField(
                    onValueChange = { viewModel.onEvent(RecruiterChanged(it)) },
                    onNextChange = { viewModel.onEvent(RecruiterChanged(it)) },
                    hint = personalInputsNameAndValue[8],
                    isError = state.recruiterError!=null,
                    errorMessage = state.recruiterError.toString(),
                    width = 150.dp,
                    inputType = InputType.TEXT

                )
            }
            item {
                CustomOutlinedTextField(
                    onValueChange = { viewModel.onEvent(RecruiterChanged(it)) },
                    onNextChange = { viewModel.onEvent(RecruiterChanged(it)) },
                    hint = personalInputsNameAndValue[9],
                    isError = state.recruiterError!=null,
                    errorMessage = state.recruiterError.toString(),
                    width = 150.dp,
                    inputType = InputType.NUMBER

                )
            }
            val educationLevel = listOf("ماجستير","بكالوريوس", "ليسنس", "معهد عالي", "معهد متوسط", "شهادة ثانوية", "شهادة اعدادية", "إبتدائية")
            item {
                Column {
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
            item {
                CustomOutlinedTextField(
                    onValueChange = { viewModel.onEvent(RecruiterChanged(it)) },
                    onNextChange = { viewModel.onEvent(RecruiterChanged(it)) },
                    hint = personalInputsNameAndValue[10],
                    isError = state.recruiterError!=null,
                    errorMessage = state.recruiterError.toString(),
                    width = 50.dp,
                    inputType = InputType.TEXT

                )
            }
        }


        CustomButton(
            text = "حفظ",
            icon = Icons.Default.Save,
            onClick = {  viewModel.onEvent(Submit) },
            buttonColor = blue
        )
    }
}
