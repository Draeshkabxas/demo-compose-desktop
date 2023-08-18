import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import common.component.SelectorWithLabel
import features.contracts.domain.model.Contract
import utils.getAllEducationArabicNames

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImportXlsxDialog(
    contractsByEducationLevel:Map<String,List<Contract>>,
    //contractsByCity:Map<String,List<Contract>> = emptyMap(),
    onError: @Composable() (String) -> Unit,
    onFilterEducationLevel:(String, List<Contract>) -> Boolean,
    onModifyEducationLevel: (Map<String, List<Contract>>, Map<String, String>) -> List<Contract>?,
    //onFilterCity:(String, List<Contract>) -> Boolean = {_,_-> true},
    //onModifyCity: (Map<String, List<Contract>>, Map<String, String>) -> List<Contract>? = {_,_->emptyList()},
    onDismiss:()->Unit,
    onAdd: (List<Contract>) -> Boolean,
) {
    var showErrorMessage by remember { mutableStateOf(false) }
    val convertMapOfEducationLevel by remember { mutableStateOf(mutableMapOf<String, String>())}
    //Fill the convert map with contracts types
    if (convertMapOfEducationLevel.isEmpty()){
        contractsByEducationLevel.filter { onFilterEducationLevel(it.key,it.value) }.forEach { convertMapOfEducationLevel[it.key] = ""}
    }

//    val convertMapOfCity by remember { mutableStateOf(mutableMapOf<String, String>())}
//    //Fill the convert map with contracts types
//    if (convertMapOfCity.isEmpty()){
//        contractsByCity.filter { onFilterCity(it.key,it.value) }.forEach { convertMapOfCity[it.key] = ""}
//    }

    CustomDialogWindow(
        onDismiss = {
            showErrorMessage = false
            onDismiss()
        },
        title = "حدد المؤهل العلمي لكل فئة",
        showErrorMessage = showErrorMessage,
        errorMessage = {
            Text("من فضلك حدد المؤهل العلمي لجميع الفئاة", color = Color.Red.copy(alpha = 0.5f))
        },
        buttons = {
            Button({
                val contractAfterModifiedEducationLevel = onModifyEducationLevel(contractsByEducationLevel, convertMapOfEducationLevel)
                if (contractAfterModifiedEducationLevel.isNullOrEmpty()) {
                    showErrorMessage = true
                } else {
                    val contracts = mutableListOf<Contract>()
                    contracts.addAll(contractAfterModifiedEducationLevel)
                    onAdd(contracts)
                }
            }) {
                Text("اضافة")
            }
        }
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(convertMapOfEducationLevel.keys.toList()) { key ->
                    var selectedItem by remember { mutableStateOf(convertMapOfEducationLevel[key] ?: "") }
                    SelectorWithLabel(
                        "إختر المؤهل العلمي لهذه الفئة ($key) ",
                        items = getAllEducationArabicNames(),
                        selectedItem = selectedItem,
                        onItemSelected = { selected ->
                            selectedItem = selected
                            convertMapOfEducationLevel[key] = selected
                        }
                    )
                }
                item{
                    if (convertMapOfEducationLevel.keys.isEmpty()){
                        Text("لا يوجد عناصر لتحويلها يرجى الضغط على اضافة لإتمام العملية")
                    }
                }
                items(convertMapOfEducationLevel.keys.toList()){

                }
            }
        }
    }
}