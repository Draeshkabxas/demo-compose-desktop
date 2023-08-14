package features.contracts.presentation.add_contracts

import androidx.compose.foundation.layout.*


import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import common.component.*
import features.contracts.domain.model.Contract
import navcontroller.NavController
import org.koin.compose.koinInject
import styles.AppColors.blueGradient
import styles.CairoTypography
import utils.Education
import utils.LibyanCities

@Composable
fun AddContractsScreen(
    navController: NavController<Screens>,
    contract: Contract? = null,
    mode:ScreenMode,
    viewModel: AddContractViewModel = koinInject(),

    ) {
    LaunchedEffect(key1 = true) {
        viewModel.validationEvents.collect { event ->
            println("event : $event")
            when (event) {
                AddContractViewModel.ValidationEvent.Success -> {
                    println("Success event : $event")
                    navController.navigateReplacement(Screens.ContractsScreen())
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
        "التبعية",
        "إسم المصرف",
        "رقم الحساب",
        "ملاحظات",
        "الرقم الاشاري"
        )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var selectededucation by remember { mutableStateOf(" إختر المؤهل ") }
        var selectedCity by remember { mutableStateOf("إختر المدينة") }
        var isFirstRender by remember { mutableStateOf(true) }
        if (isFirstRender){
            viewModel.setupMode(mode,contract)
            selectededucation = viewModel.state.educationLevel
            selectedCity = viewModel.state.city
            isFirstRender = false
        }

        HeadLineWithDate(text = "منظومة العقود / إضافة ملف ", date ="1/7/2023  1:30:36 PM" )
        Section("البيانات الشخصية",
            3){
            val state = viewModel.state
            item {
                CustomOutlinedTextField(
                    value = state.name,
                    onValueChange = { viewModel.onEvent(ContractInfoFormEvent.NameChanged(it)) },
                    onNextChange = { viewModel.onEvent(ContractInfoFormEvent.NameChanged(it)) },
                    isError = state.nameError!=null,
                    hint = personalInputsNameAndValue[0],
                    errorMessage = state.nameError.toString(),
                    width = 150.dp,
                    inputType = InputType.TEXT
                )
            }
            item {
                CustomOutlinedTextField(
                    value = state.motherName,
                    onValueChange = { viewModel.onEvent(ContractInfoFormEvent.MotherNameChanged(it)) },
                    onNextChange = { viewModel.onEvent(ContractInfoFormEvent.MotherNameChanged(it)) },
                    hint = personalInputsNameAndValue[1],
                    isError = state.motherNameError!=null,
                    errorMessage = state.motherNameError.toString(),
                    width = 150.dp,
                    inputType = InputType.TEXT

                )
            }
            item {
                CustomOutlinedTextField(
                    value = state.motherNationality,
                    onValueChange = { viewModel.onEvent(ContractInfoFormEvent.MotherNationalityChanged(it)) },
                    onNextChange = { viewModel.onEvent(ContractInfoFormEvent.MotherNationalityChanged(it)) },
                    hint = personalInputsNameAndValue[2],
                    isError = state.motherNationalityError!=null,
                    errorMessage = state.motherNationalityError.toString(),
                    width = 150.dp,
                    inputType = InputType.TEXT
                )
            }
            item {
                CustomOutlinedTextField(
                    value = state.fileNumber,
                    onValueChange = { viewModel.onEvent(ContractInfoFormEvent.FileNumberChanged(it)) },
                    onNextChange = { viewModel.onEvent(ContractInfoFormEvent.FileNumberChanged(it)) },
                    hint = personalInputsNameAndValue[3],
                    isError = state.fileNumberError!=null,
                    errorMessage = state.fileNumberError.toString(),
                    width = 150.dp,
                    inputType = InputType.All,
                    maxLength = 8 // Set the maximum length to N characters

                )
            }
            item {
                CustomOutlinedTextField(
                    value = state.libyaId,
                    onValueChange = { viewModel.onEvent(ContractInfoFormEvent.LibyaIdChanged(it)) },
                    onNextChange = { viewModel.onEvent(ContractInfoFormEvent.LibyaIdChanged(it)) },
                    hint = personalInputsNameAndValue[4],
                    isError = state.libyaIdError!=null,
                    errorMessage = state.libyaIdError.toString(),
                    width = 150.dp,
                    inputType = InputType.NUMBER,
                    maxLength = 12 // Set the maximum length to N characters
                )
            }
            item {
                CustomOutlinedTextField(
                    value = state.phoneNumber,
                    onValueChange = { viewModel.onEvent(ContractInfoFormEvent.PhoneNumberChanged(it)) },
                    onNextChange = { viewModel.onEvent(ContractInfoFormEvent.PhoneNumberChanged(it)) },
                    hint = personalInputsNameAndValue[5],
                    isError = state.phoneNumberError!=null,
                    errorMessage = state.phoneNumberError.toString(),
                    width = 150.dp,
                    inputType = InputType.NUMBER,
                    maxLength = 10 // Set the maximum length to N characters

                )
            }
            item {
                CustomOutlinedTextField(
                    value = state.dependency,
                    onValueChange = { viewModel.onEvent(ContractInfoFormEvent.DependencyChanged(it)) },
                    onNextChange = { viewModel.onEvent(ContractInfoFormEvent.DependencyChanged(it)) },
                    hint = personalInputsNameAndValue[6],
                    isError = state.dependencyError!=null,
                    errorMessage = state.dependencyError.toString(),
                    width = 150.dp,
                    inputType = InputType.TEXT
                )
            }

            val educationLevel = listOf("ماجستير","بكالوريوس", "ليسنس", "معهد عالي", "معهد متوسط", "شهادة ثانوية", "شهادة اعدادية", "إبتدائية")
            item {
                Column {
                    SelectorWithLabel(
                        label = "المؤهل العلمي : ",
                        items = Education.values().map { it.arabicName },
                        selectedItem = if (selectededucation.isEmpty()) "إختر المؤهل " else selectededucation ,
                        onItemSelected = {
                            selectededucation = it
                            viewModel.onEvent(ContractInfoFormEvent.EducationLevelChanged(it))}
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
                            selectedCity= it
                            viewModel.onEvent(ContractInfoFormEvent.CityChanged(it)) }
                    )
                    if (state.cityError!=null)
                        Text(state.cityError.toString(),
                            color = Color.Red,
                            style = CairoTypography.body2
                        )
                }
            }

        }

        Section("البيانات المالية",
            3){
            val state = viewModel.state
            item {
                CustomOutlinedTextField(
                    value = state.bankName,
                    onValueChange = { viewModel.onEvent(ContractInfoFormEvent.BankNameChanged(it)) },
                    onNextChange = { viewModel.onEvent(ContractInfoFormEvent.BankNameChanged(it)) },
                    hint = personalInputsNameAndValue[7],
                    isError = state.bankNameError!=null,
                    errorMessage = state.bankNameError.toString(),
                    width = 150.dp,
                    inputType = InputType.TEXT

                )
            }
            item {
                CustomOutlinedTextField(
                    value = state.accountNumber,
                    onValueChange = { viewModel.onEvent(ContractInfoFormEvent.AccountNumberChanged(it)) },
                    onNextChange = { viewModel.onEvent(ContractInfoFormEvent.AccountNumberChanged(it)) },
                    hint = personalInputsNameAndValue[8],
                    isError = state.accountNumberError!=null,
                    errorMessage = state.accountNumberError.toString(),
                    width = 150.dp,
                    inputType = InputType.NUMBER

                )
            }
            item {
                CustomOutlinedTextField(
                    value = state.notes,
                    onValueChange = { viewModel.onEvent(ContractInfoFormEvent.NotesChanged(it)) },
                    onNextChange = { viewModel.onEvent(ContractInfoFormEvent.NotesChanged(it)) },
                    hint = personalInputsNameAndValue[9],
                    isError = state.notesError!=null,
                    errorMessage = state.notesError.toString(),
                    width = 50.dp,
                    inputType = InputType.TEXT
                )
            }
            item {
                CustomOutlinedTextField(
                    value = state.reference,
                    onValueChange = { viewModel.onEvent(ContractInfoFormEvent.ReferenceChanged(it)) },
                    onNextChange = { viewModel.onEvent(ContractInfoFormEvent.ReferenceChanged(it)) },
                    hint = personalInputsNameAndValue[10],
                    isError = state.referenceError!=null,
                    errorMessage = state.referenceError.toString(),
                    width = 50.dp,
                    inputType = InputType.All
                )
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        GradientButton(
            text = "حفظ",
            icon = Icons.Default.Save,
            onClick = {  viewModel.onEvent(ContractInfoFormEvent.Submit(mode)) },
            colors = blueGradient,
            cornerRadius = 30.dp
        )

    }
}
