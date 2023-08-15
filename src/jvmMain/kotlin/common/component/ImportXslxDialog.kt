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
    contracts:Map<String,List<Contract>>,
    onError: @Composable() (String) -> Unit,
    onFilter:(String,List<Contract>) -> Boolean,
    onDismiss:()->Unit,
    onModify: (Map<String, List<Contract>>, Map<String, String>) -> List<Contract>?,
    onAdd: (List<Contract>) -> Boolean,
) {
    var showErrorMessage by remember { mutableStateOf(false) }
    val convertMap by remember { mutableStateOf(mutableMapOf<String, String>())}
    //Fill the convert map with contracts types
    if (convertMap.isEmpty()){
        contracts.filter { onFilter(it.key,it.value) }.forEach { convertMap[it.key] = ""}
    }

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
                val contractAfterModified = onModify(contracts, convertMap)
                if (contractAfterModified.isNullOrEmpty()) {
                    showErrorMessage = true
                } else {
                    onAdd(contractAfterModified)
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
                items(convertMap.keys.toList()) { key ->
                    var selectedItem by remember { mutableStateOf(convertMap[key] ?: "") }
                    SelectorWithLabel(
                        "إختر المؤهل العلمي لهذه الفئة ($key) ",
                        items = getAllEducationArabicNames(),
                        selectedItem = selectedItem,
                        onItemSelected = { selected ->
                            selectedItem = selected
                            convertMap[key] = selected
                        }
                    )
                }
                item{
                    if (convertMap.keys.isEmpty()){
                        Text("لا يوجد عناصر لتحويلها يرجى الضغط على اضافة لإتمام العملية")
                    }
                }
            }
        }
    }
}