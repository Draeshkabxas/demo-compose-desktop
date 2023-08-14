package features.results.presentation.add_results

import AlertSystem.presentation.showSuccessMessage
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
import common.component.ScreenMode.EDIT
import features.results.domain.model.Results
import navcontroller.NavController
import org.koin.compose.koinInject
import styles.AppColors.blueGradient
import styles.CairoTypography

@Composable
fun AddResultsScreen(
    navController: NavController<Screens>,
    results: Results? = null,
    mode:ScreenMode,
    viewModel: AddResultsViewModel = koinInject(),

    ) {
    LaunchedEffect(key1 = true) {
        viewModel.validationEvents.collect { event ->
            println("event : $event")
            when (event) {
                AddResultsViewModel.ValidationEvent.Success -> {
                    if(mode == EDIT){
                        "تمت عملية التعديل بنجاح".showSuccessMessage()
                    }else{
                        "تمت عملية الاضافة بنجاح".showSuccessMessage()
                    }
                    navController.navigateReplacement(Screens.ResultsScreen())
                }
                else -> {}
            }
        }
    }
    val personalInputsNameAndValue = listOf(
        "الاسم رباعي",
//        "اسم الام",
//        "جنسية الأم",
//        "رقم الملف",
//        "الرقم الوطني",
        "رقم الهاتف",
//        "المؤهل العلمي",
        "نتيجة التحاليل",
//        "إسم المصرف",
//        "رقم الحساب",
        "تاريخ التحاليل",
        "ملاحظات"
        )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var selectedresults by remember { mutableStateOf(" إختر النتيجة ") }
//        var selectedCity by remember { mutableStateOf("إختر المدينة") }
        var isFirstRender by remember { mutableStateOf(true) }
        if (isFirstRender){
            viewModel.setupMode(mode,results)
            selectedresults = viewModel.state.result
//            selectedCity = viewModel.state.city
            isFirstRender = false
        }

        HeadLineWithDate(text = "منظومة نتائج التحاليل / إضافة جديد ", date ="1/7/2023  1:30:36 PM" )
        Section("البيانات الشخصية",
            3){
            val state = viewModel.state
            item {
                CustomOutlinedTextField(
                    value = state.name,
                    onValueChange = { viewModel.onEvent(ResultsInfoFormEvent.NameChanged(it)) },
                    onNextChange = { viewModel.onEvent(ResultsInfoFormEvent.NameChanged(it)) },
                    isError = state.nameError!=null,
                    hint = personalInputsNameAndValue[0],
                    errorMessage = state.nameError.toString(),
                    width = 150.dp,
                    inputType = InputType.TEXT
                )
            }
//            item {
//                CustomOutlinedTextField(
//                    value = state.motherName,
//                    onValueChange = { viewModel.onEvent(ContractInfoFormEvent.MotherNameChanged(it)) },
//                    onNextChange = { viewModel.onEvent(ContractInfoFormEvent.MotherNameChanged(it)) },
//                    hint = personalInputsNameAndValue[1],
//                    isError = state.motherNameError!=null,
//                    errorMessage = state.motherNameError.toString(),
//                    width = 150.dp,
//                    inputType = InputType.TEXT
//
//                )
//            }
//            item {
//                CustomOutlinedTextField(
//                    value = state.motherNationality,
//                    onValueChange = { viewModel.onEvent(ContractInfoFormEvent.MotherNationalityChanged(it)) },
//                    onNextChange = { viewModel.onEvent(ContractInfoFormEvent.MotherNationalityChanged(it)) },
//                    hint = personalInputsNameAndValue[2],
//                    isError = state.motherNationalityError!=null,
//                    errorMessage = state.motherNationalityError.toString(),
//                    width = 150.dp,
//                    inputType = InputType.TEXT
//                )
//            }
//            item {
//                CustomOutlinedTextField(
//                    value = state.fileNumber,
//                    onValueChange = { viewModel.onEvent(ContractInfoFormEvent.FileNumberChanged(it)) },
//                    onNextChange = { viewModel.onEvent(ContractInfoFormEvent.FileNumberChanged(it)) },
//                    hint = personalInputsNameAndValue[3],
//                    isError = state.fileNumberError!=null,
//                    errorMessage = state.fileNumberError.toString(),
//                    width = 150.dp,
//                    inputType = InputType.All,
//                    maxLength = 8 // Set the maximum length to N characters
//
//                )
//            }
//            item {
//                CustomOutlinedTextField(
//                    value = state.libyaId,
//                    onValueChange = { viewModel.onEvent(ContractInfoFormEvent.LibyaIdChanged(it)) },
//                    onNextChange = { viewModel.onEvent(ContractInfoFormEvent.LibyaIdChanged(it)) },
//                    hint = personalInputsNameAndValue[4],
//                    isError = state.libyaIdError!=null,
//                    errorMessage = state.libyaIdError.toString(),
//                    width = 150.dp,
//                    inputType = InputType.NUMBER,
//                    maxLength = 12 // Set the maximum length to N characters
//                )
//            }
            item {
                CustomOutlinedTextField(
                    value = state.phoneNumber,
                    onValueChange = { viewModel.onEvent(ResultsInfoFormEvent.PhoneNumberChanged(it)) },
                    onNextChange = { viewModel.onEvent(ResultsInfoFormEvent.PhoneNumberChanged(it)) },
                    hint = personalInputsNameAndValue[1],
                    isError = state.phoneNumberError!=null,
                    errorMessage = state.phoneNumberError.toString(),
                    width = 150.dp,
                    inputType = InputType.NUMBER,
                    maxLength = 10 // Set the maximum length to N characters
                )
            }




//            item{
//                Column {
//                    SelectorWithLabel(
//                        label = "المدينة : ",
//                        items = LibyanCities.values().map { it.arabicName },
//                        selectedItem = if (selectedCity.isEmpty()) "إختر المدينة " else selectedCity ,
//                        onItemSelected = {
//                            selectedCity= it
//                            viewModel.onEvent(ResultsInfoFormEvent.CityChanged(it)) }
//                    )
//                    if (state.cityError!=null)
//                        Text(state.cityError.toString(),
//                            color = Color.Red,
//                            style = CairoTypography.body2
//                        )
//                }
//            }

        }

        Section("بيانات التحاليل",
            3){
            val state = viewModel.state
//            item {
//                CustomOutlinedTextField(
//                    value = state.result,
//                    onValueChange = { viewModel.onEvent(ResultsInfoFormEvent.ResultChanged(it)) },
//                    onNextChange = { viewModel.onEvent(ResultsInfoFormEvent.ResultChanged(it)) },
//                    hint = personalInputsNameAndValue[2],
//                    isError = state.resultError!=null,
//                    errorMessage = state.resultError.toString(),
//                    width = 150.dp,
//                    inputType = InputType.TEXT
//                )
//            }
            val testsResult = listOf("لائق","غير لائق")
            item {
                Column {
                    SelectorWithLabel(
                        label = "نتيجة التحاليل : ",
                        items = testsResult,
                        selectedItem = if (selectedresults.isEmpty()) "إختر النتيجة " else selectedresults ,
                        onItemSelected = {
                            selectedresults = it
                            viewModel.onEvent(ResultsInfoFormEvent.ResultChanged(it))}
                    )

                    if (state.resultError != null){
                        Text(state.resultError.toString()
                            ,color = Color.Red,
                            style = CairoTypography.body2)}
                }
            }
//            item {
//                CustomOutlinedTextField(
//                    value = state.bankName,
//                    onValueChange = { viewModel.onEvent(ResultsInfoFormEvent.BankNameChanged(it)) },
//                    onNextChange = { viewModel.onEvent(ResultsInfoFormEvent.BankNameChanged(it)) },
//                    hint = personalInputsNameAndValue[7],
//                    isError = state.bankNameError!=null,
//                    errorMessage = state.bankNameError.toString(),
//                    width = 150.dp,
//                    inputType = InputType.TEXT
//
//                )
//            }
            item {
                CustomOutlinedTextField(
                    value = state.date,
                    onValueChange = { viewModel.onEvent(ResultsInfoFormEvent.DateChanged(it)) },
                    onNextChange = { viewModel.onEvent(ResultsInfoFormEvent.DateChanged(it)) },
                    hint = personalInputsNameAndValue[3],
                    isError = state.dateError!=null,
                    errorMessage = state.dateError.toString(),
                    width = 150.dp,
                    inputType = InputType.All

                )
            }
            item {
                CustomOutlinedTextField(
                    value = state.notes,
                    onValueChange = { viewModel.onEvent(ResultsInfoFormEvent.NotesChanged(it)) },
                    onNextChange = { viewModel.onEvent(ResultsInfoFormEvent.NotesChanged(it)) },
                    hint = personalInputsNameAndValue[4],
                    isError = state.notesError!=null,
                    errorMessage = state.notesError.toString(),
                    width = 50.dp,
                    inputType = InputType.TEXT
                )
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        GradientButton(
            text = "حفظ",
            icon = Icons.Default.Save,
            onClick = {  viewModel.onEvent(ResultsInfoFormEvent.Submit(mode)) },
            colors = blueGradient,
            cornerRadius = 30.dp
        )

    }
}
