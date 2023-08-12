import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import common.component.SelectorWithLabel
import features.contracts.domain.model.Contract
import utils.Education
import utils.getAllEducationArabicNames
import java.awt.FileDialog
import java.awt.Frame

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImportXlsxDialog(
    contracts:Map<String,List<Contract>>,
    onError: @Composable() (String) -> Unit,
    onDismiss:()->Unit,
    onModify: (Map<String, List<Contract>>, Map<String, String>) -> List<Contract>?,
    onAdd: (List<Contract>) -> Boolean,
) {
    val convertMap by remember { mutableStateOf(mutableMapOf<String, String>())}
    //Fill the convert map with contracts types
    contracts.forEach { convertMap[it.key] = ""}

    var showErrorMessage by remember { mutableStateOf(false) }
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
        LazyColumn {
            items(contracts.keys.toList()) {key->
                var selectedItem by remember { mutableStateOf(convertMap[key] ?: "") }
                SelectorWithLabel(
                    "إختر المؤهل العلمي لهذه الفئة ($key) ",
                    items = getAllEducationArabicNames(),
                    selectedItem = selectedItem,
                    onItemSelected = {selected->
                        selectedItem = selected
                        convertMap[key] = selected
                    }
                )
            }
        }
    }
}