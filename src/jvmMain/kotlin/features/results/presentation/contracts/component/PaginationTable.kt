package features.results.presentation.results.component

import AlertSystem.presentation.showErrorMessage
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import common.component.ItemMenu
import common.component.ScreenMode
import common.component.Screens
import features.results.domain.model.Results
import features.sons_of_officers.domain.model.Person
import navcontroller.NavController
import styles.AppColors
import styles.AppColors.blue
import styles.CairoTypography
import utils.getUserAuth

@Composable
fun PaginatedTable(
    navController: NavController<Screens>,
    headers: List<String>,
    resultsList: List<Results>,
    itemsPerPage: Int,
    columnWidths: List<Dp>,
    onRemoveResults: (Results) -> Unit,
    currentPage:MutableState<Int> = mutableStateOf(0),
    onSelectedListChange:(MutableList<Results>) -> Unit,
    checkedList: List<Results>
    ) {
//    if (contractList.isEmpty()) return
    val pageCount = (resultsList.size + itemsPerPage - 1) / itemsPerPage
    //btn check
    var isButtonVisible by remember { mutableStateOf(false) }
    val userAuthSystem = getUserAuth()
    var canEditPermission = userAuthSystem.canEdit()
    var superAdmin = userAuthSystem.canChangeAccountsPermission()

    val scrollState = rememberScrollState()
    val scrollBarAdapter = rememberScrollbarAdapter(scrollState)
    val selectedList  = mutableStateListOf<Results>()
    selectedList.addAll(checkedList)
    Box(
        modifier = Modifier.horizontalScroll(scrollState)  .fillMaxWidth()
    ) {
        Column(        modifier = Modifier.fillMaxWidth()

        ) {
        Row {
            headers.forEachIndexed { index, header ->
                Text(
                    text = header,
                    style = CairoTypography.h3,
                    color = Color.White,
                    maxLines = 1,
                    modifier = Modifier
                        .width(columnWidths[index])
                        .background(blue)
//                        .border(0.5.dp, Color.White)
                        .padding(8.dp)
                )
            }
        }
        if (resultsList.isNullOrEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
                Column(
                    modifier = Modifier.fillMaxWidth().sizeIn(maxHeight = 200.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.width(1200.dp))
                    Text(text = "لاتوجد نتائج للبحث  ", style = CairoTypography.h3)
                    Text(text = "يمكنك فلترة بحثك للحصول على نتائج اكثر دقة", style = CairoTypography.h3)
                }
            }
        } else {
            var counter = 0 // initialize counter based on current page and items per page
            LazyColumn {
                items(resultsList.chunked(itemsPerPage)[currentPage.value]) { results ->
                    val showPopup = remember { mutableStateOf(false) }
                    val showDialog = remember { mutableStateOf(false) }

                    Row(
                        modifier = Modifier.background(
                            if (results.result =="لائق"||results.result =="لايق") Color.Green.copy(alpha = 0.20f) else
                                if (results.result=="غير لائق" ||results.result =="غيرلائق" || results.result=="غير لايق" ||results.result =="غيرلايق") Color.Red.copy(alpha = 0.20f) else
                            if ((currentPage.value % 2 == 0 && resultsList.indexOf(results) % 2 == 0) ||
                                (currentPage.value % 2 != 0 && resultsList.indexOf(results) % 2 != 0)
                            )
                                Color.White else Color.White
                        )
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = { offset ->
                                        showPopup.value = true
                                    },
                                    onLongPress = { offset ->
                                        showPopup.value = true
                                    }
                                )
                            }
                    ) {

                        if (superAdmin) {
                            ItemMenu(
                                showMenu = showPopup,
                                onEdit = {
                                    navController.navigate(
                                        Screens.AddResultsScreen(
                                            mode = ScreenMode.EDIT,
                                            results = results
                                        )
                                    )

                                }, onRemove = {
                                    onRemoveResults(results)
                                    ("تمت عملية مسح الملف بنجاح").showErrorMessage()
                                },
                                showDialog = showDialog,
                                alertText = "هل انت متأكد من أنك تريد مسح هذا الملف ؟"
                            )
                        }
                        Checkbox(
                            checked = selectedList.contains(results),
                            colors = CheckboxDefaults.colors(
                                checkedColor = blue,
                                uncheckedColor = Color.Gray,
                                checkmarkColor = Color.White,
                                disabledColor = Color.LightGray,
                            ),
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    selectedList.add(results)
                                } else {
                                    selectedList.remove(results)
                                }
                                onSelectedListChange(selectedList)
                            },
                            modifier = Modifier
                                .width(columnWidths[0])
                                .padding(8.dp)
                        )
                        Text(
                            text = (resultsList.indexOf(results) + 1).toString(), // display counter value as text
                            maxLines = 1,
                            style = CairoTypography.h3,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(columnWidths[1])
                                .padding(8.dp)
                        )
//                        Text(
//                            text = contract.fileNumber,
//                            style = CairoTypography.h3,
//                            fontWeight = FontWeight.Bold,
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier
//                                .width(columnWidths[1])
//                                .padding(8.dp)
//                        )
                        Text(
                            text = results.name,
                            style = CairoTypography.h3,

                            fontWeight = FontWeight.Bold,
//                            textAlign = TextAlign.,
                            maxLines = 1,

                            modifier = Modifier
                                .width(columnWidths[2])
                                .padding(8.dp)
                        )
//                        Text(
//                            text = contract.libyaId,
//                            style = CairoTypography.h4,
//                            fontWeight = FontWeight.Bold,
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier
//                                .width(columnWidths[3])
//                                .padding(8.dp)
//                        )
//                        Text(
//                            text = contract.motherName,
//                            style = CairoTypography.h4,
//                            fontWeight = FontWeight.Bold,
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier
//                                .width(columnWidths[4])
//                                .padding(8.dp)
//                        )
//                        Text(
//                            text = contract.motherNationality,
//                            style = CairoTypography.h3,
//                            fontWeight = FontWeight.Bold,
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier
//                                .width(columnWidths[5])
//                                .padding(8.dp)
//                        )
//                        Text(
//                            text = contract.educationLevel,
//                            style = CairoTypography.h4,
//                            fontWeight = FontWeight.Bold,
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier
//                                .width(columnWidths[6])
//                                .padding(8.dp)
//                        )
//                        Text(
//                            text = contract.date,
//                            style = CairoTypography.h4,
//                            fontWeight = FontWeight.Bold,
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier
//                                .width(columnWidths[7])
//                                .padding(8.dp)
//                        )
                        Text(
                            text = results.phoneNumber,
                            style = CairoTypography.h3,
                            fontWeight = FontWeight.Bold,
//                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            modifier = Modifier
                                .width(columnWidths[3])
                                .padding(8.dp)
                        )
                        Text(
                            text = results.result,
                            style = CairoTypography.h3,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
//                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(columnWidths[4])
                                .padding(8.dp)
                        )
                        Text(
                            text = results.date,
                            style = CairoTypography.h3,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
//                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(columnWidths[5])
                                .padding(8.dp)
                        )
//                        Text(
//                            text = contract.accountNumber,
//                            style = CairoTypography.h4,
//                            fontWeight = FontWeight.Bold,
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier
//                                .width(columnWidths[11])
//                                .padding(8.dp)
//                        )

//                        if (canEditPermission) {
//                            Spacer(modifier = Modifier.size(0.dp, 20.dp))
//
//                            common.component.TextButton(
//                                width = columnWidths[5],
//                                textSize = 10.sp,
//                                text = "إضافة",
//                                onClick = {
//                                    navController.navigate(
//                                        Screens.AddResultsScreen(
//                                            mode = ScreenMode.EDIT,
//                                            results = results
//                                        )
//                                    )
//
//                                },
//                                colors = AppColors.GreenGradient, cornerRadius = 30.dp
//                            )
//                        }
                        Text(
                            text = results.notes,
                            style = CairoTypography.h3,
                            fontWeight = FontWeight.Bold,
//                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(columnWidths[6])
                                .padding(8.dp),
                            maxLines = 1
                        )

                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(500.dp))
            Button(
                shape = RoundedCornerShape(20.dp),
                onClick = { if (currentPage.value > 0) currentPage.value-- }) {
                Text(
                    "السابقة",
                    style = CairoTypography.h4,
                    fontWeight = FontWeight.Bold

                )
            }
            Text(
                "الصفحة ${currentPage.value + 1} من $pageCount",
                style = CairoTypography.h4,
                fontWeight = FontWeight.Bold,

                modifier = Modifier.padding(8.dp)
            )
            Button(
                shape = RoundedCornerShape(20.dp),
                onClick = { if (currentPage.value < pageCount - 1) currentPage.value++ }) {
                Text(
                    "التالية",
                    style = CairoTypography.h4,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}}