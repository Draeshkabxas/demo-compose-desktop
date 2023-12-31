package common.component

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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import common.component.ScreenMode.EDIT
import common.component.Screens.AddSonsOfOfficersScreen
import features.courses.domain.model.Course
import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.presentation.sons_of_officers.SonsOfOfficersScreenViewModel
import navcontroller.NavController
import styles.AppColors
import styles.AppColors.blue
import styles.CairoTypography
import utils.getUserAuth

@Composable
fun PaginatedTable(
    navController: NavController<Screens>,
    headers: List<String>,
    personList: List<Person>,
    itemsPerPage: Int,
    columnWidths: List<Dp>,
    onRemovePerson: (Person) -> Unit,
    currentPage:MutableState<Int> = mutableStateOf(0),
    onSelectedListChange:(MutableList<Person>) -> Unit ,
    checkedList: List<Person>
) {
//    if (personList.isEmpty()) return
    val pageCount = (personList.size + itemsPerPage - 1) / itemsPerPage
    //btn check
    var isButtonVisible by remember { mutableStateOf(false) }
    val userAuthSystem = getUserAuth()
    var canEditPermission = userAuthSystem.canEdit()
    var superAdmin = userAuthSystem.canChangeAccountsPermission()

    val scrollState = rememberScrollState()
    val scrollBarAdapter = rememberScrollbarAdapter(scrollState)
    val selectedList  = mutableStateListOf<Person>()
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
                    style = CairoTypography.h4,
                    color = Color.White,
                    modifier = Modifier
                        .width(columnWidths[index])
                        .background(blue)
//                        .border(0.5.dp, Color.White)
                        .padding(8.dp)
                )
            }
        }
        if (personList.isEmpty()) {
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
            LazyColumn {
                items(personList.chunked(itemsPerPage)[currentPage.value]) { person ->
                    val showPopup = remember { mutableStateOf(false) }
                    val showDialog = remember { mutableStateOf(false) }

                    Row(
                        modifier = Modifier
                            .background(
                                if (person.procedures["لائق صحيا"] == true) Color.Green.copy(alpha = 0.20f) else
                                    if (person.procedures["غير لائق صحيا"] == true) Color.Red.copy(alpha = 0.20f) else
                                        if ((currentPage.value % 2 == 0 && personList.indexOf(person) % 2 == 0) ||
                                            (currentPage.value % 2 != 0 && personList.indexOf(person) % 2 != 0)
                                        )
                                            Color.White else Color.White
                            )
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = { _ ->
                                        showPopup.value = true
                                    },
                                    onLongPress = { _ ->
                                        showPopup.value = true
                                    }
                                )
                            }
                    ) {
//                        if (superAdmin){
//                            ItemMenu(
//                                showMenu = showPopup,
//                                onEdit = {
//                                    navController.navigate(AddSonsOfOfficersScreen(mode = EDIT, person = person))
//                                }, onRemove = {
//                                    onRemovePerson(person)
//                                },
//                                showDialog = showDialog,
//                                alertText = "هل انت متأكد من أنك تريد مسح هذا الملف ؟"
//                            )
//                        }
                        ItemMenu(
                            showMenu = showPopup,
                            onEdit = {
                                navController.navigate(AddSonsOfOfficersScreen(mode = EDIT, person = person))


                            },

                            onRemove = {

                                onRemovePerson(person)
                                ("تمت عملية مسح الملف بنجاح").showErrorMessage()

                            },

                            showDialog = showDialog,
                            alertText = "هل انت متأكد من أنك تريد مسح هذا الملف ؟"
                        )
                        Checkbox(
                            checked = selectedList.contains(person),
                            colors = CheckboxDefaults.colors(
                                checkedColor = blue,
                                uncheckedColor = Color.Gray,
                                checkmarkColor = Color.White,
                                disabledColor = Color.LightGray,
                            ),
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    selectedList.add(person)
                                } else {
                                    selectedList.remove(person)
                                }
                                onSelectedListChange(selectedList)
                            },
                            modifier = Modifier
                                .width(columnWidths[0])
                                .padding(8.dp)
                        )
                        Text(
                            text = (personList.indexOf(person) + 1).toString(), // display counter value as text
                            maxLines = 1,
                            style = CairoTypography.h3,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(columnWidths[1])
                                .padding(8.dp)
                        )
                        Text(
                            text = person.fileNumber,
                            style = CairoTypography.h3,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            maxLines = 1,

                            modifier = Modifier
                                .width(columnWidths[2])
                                .padding(8.dp)
                        )
                        Text(
                            text = person.name,
                            style = CairoTypography.h4,
                            fontWeight = FontWeight.Bold,
//                            textAlign = TextAlign.Center,
                            maxLines = 1,

                            modifier = Modifier
                                .width(columnWidths[3])
                                .padding(8.dp)
                        )
                        Text(
                            text = person.libyaId,
                            style = CairoTypography.h4,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(columnWidths[4])
                                .padding(8.dp)
                        )
                        Text(
                            text = person.motherName,
                            style = CairoTypography.h4,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,

//                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(columnWidths[5])
                                .padding(8.dp)
                        )
                        Text(
                            text = person.educationLevel,
                            style = CairoTypography.h4,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            maxLines = 1,

                            modifier = Modifier
                                .width(columnWidths[6])
                                .padding(8.dp)
                        )
                        Text(
                            text = person.city,
                            style = CairoTypography.h4,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            maxLines = 1,

                            modifier = Modifier
                                .width(columnWidths[7])
                                .padding(8.dp)
                        )
                        Text(
                            text = person.phoneNumber,
                            style = CairoTypography.h4,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            maxLines = 1,

                            modifier = Modifier
                                .width(columnWidths[8])
                                .padding(8.dp)
                        )
                        Text(
                            text = person.recruiter,
                            style = CairoTypography.h4,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,

//                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(columnWidths[9])
                                .padding(8.dp)
                        )

                        Text(
                            text = if (person.justificationsRequire.values.contains(false)) {
                                "نواقص"
                            } else {
                                "مستوفي"
                            },
                            style = CairoTypography.h4,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            maxLines = 1,

                            modifier = Modifier
                                .width(columnWidths[10])
                                .padding(8.dp)
                        )
                        //val justifications = person.justificationsRequire.filterValues { it }.keys
                        val valueToCheck = person.procedures.get("إحالة لتدريب")

//                    isButtonVisible = person.hasShortfalls()
//                        if (canEditPermission) {
//                            if (valueToCheck == false) {
//                                Spacer(modifier = Modifier.size(0.dp, 20.dp))
//
//                                TextButton(
//                                    width = columnWidths[10],
//                                    text = "إضافة",
//                                    onClick = {
//                                        navController.navigate(AddSonsOfOfficersScreen(mode = EDIT, person = person))
//                                    },
//                                    colors = AppColors.GreenGradient, cornerRadius = 30.dp
//                                )
//                            }
//                        } else {
//                            Text(
//                                text = "",
//                                modifier = Modifier
//                                    .width(columnWidths[10])
//                                    .padding(8.dp)
//                            )
//                        }
                        Text(
                            text = person.commission,
                            style = CairoTypography.h4,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            maxLines = 1,

                            modifier = Modifier
                                .width(columnWidths[11])
                                .padding(8.dp)
                        )

                        Text(
                            text = if (person.procedures["لائق صحيا"] == true) "لائق" else if (person.procedures["غير لائق صحيا"] == true) "غير لائق" else "",
                            style = CairoTypography.h4,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            maxLines = 1,

                            modifier = Modifier
                                .width(columnWidths[12])
                                .padding(8.dp)
                        )
                        Text(
                            text = person.notes,
                            style = CairoTypography.h4,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,

//                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(columnWidths[13])
                                .padding(8.dp)
                        )
                        Text(
                            text = if (person.procedures["إحالة لتدريب"] != null &&
                                person.procedures["إحالة لتدريب"] == true
                            ) {
                                "نعم"
                            } else {
                                "لا"
                            },
                            style = CairoTypography.h4,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            maxLines = 1,

                            modifier = Modifier
                                .width(columnWidths[14])
                                .padding(8.dp)
                        )
//                    counter++
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
            Spacer(modifier = Modifier.width(700.dp))

            Button(elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                hoveredElevation = 15.dp,
                pressedElevation = 15.dp
            ),
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
