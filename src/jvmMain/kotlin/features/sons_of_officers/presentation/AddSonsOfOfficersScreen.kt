package features.sons_of_officers.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.component.RoundedButton
import common.component.RoundedDropdownMenu
import navcontroller.NavController
import common.component.CheckBoxWithLabel
import common.component.CustomOutlinedTextField
import common.component.Section
import features.sons_of_officers.presentation.PersonalInfoFormEvent.*
import org.koin.compose.koinInject

@Composable
fun AddSonsOfOfficersScreen(
    navController: NavController,
    viewModel: AddSonsOfOfficersViewModel = koinInject()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Section("البيانات الشخصية",3){
            viewModel.personalInputsNameAndValue.forEachIndexed {  index,item ->
                if (item != "المؤهل العلمي" && item!= "المدينة"){
                    item {
                        CustomOutlinedTextField(
                            onValueChange = { viewModel.onEvent(viewModel.getEvent(index,it)) },
                            onNextChange = { viewModel.onEvent(viewModel.getEvent(index,it)) },
                            hint = item,
                            errorMessage = viewModel.state.cityError
                        )
                    }
                }
            }
            val educationLevel = listOf("بكالوريس","ماجستير","دبلوم عالي")
            item {
                RoundedDropdownMenu(
                    items = educationLevel,
                    onSelectItem = { viewModel.onEvent(EducationLevelChanged(it))},
                    label = { Text("المؤهل العلمي") }
                ){
                    Text(it)
                }
                Text(viewModel.personalInputsNameAndValue[5].check())
            }
            val  cities= listOf("tripoli","masrath","sabratha","regdalin")
            item{
                RoundedDropdownMenu(
                    items = cities,
                    onSelectItem = {viewModel.onEvent(CityChanged(it))},
                    label = { Text("المدينة") }
                ){
                    Text(it)
                }
                Text(viewModel.personalInputsNameAndValue[7].check())
            }
        }
        Section("المسوغات المطلوبة",3){
            viewModel.justificationsRequiredInputsNameAndValue.forEach{name,valueState ->
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
