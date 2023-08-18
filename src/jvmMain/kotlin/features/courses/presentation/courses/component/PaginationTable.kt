package features.courses.presentation.courses.component

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
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import common.component.ItemMenu
import common.component.ScreenMode.EDIT
import common.component.Screens
import features.courses.domain.model.Course
import features.courses.domain.usecases.RemoveCourse
import navcontroller.NavController
import styles.AppColors
import styles.CairoTypography
import utils.getUserAuth
import kotlin.math.roundToInt

@Composable
fun PaginatedTable(
    navController: NavController<Screens>,
    headers: List<String>,
    personList: List<Course>,
    itemsPerPage: Int,
    columnWidths: List<Dp>,
    onRemoveCourse: (Course) -> Unit
) {
//    if (personList.isEmpty()) return
    val pageCount = (personList.size + itemsPerPage - 1) / itemsPerPage
    var currentPage by remember { mutableStateOf(0) }
    //btn check
    var isButtonVisible by remember { mutableStateOf(false) }
    val userAuthSystem = getUserAuth()
    val canEditPermission = userAuthSystem.canEdit()
    val superAdmin = userAuthSystem.canChangeAccountsPermission()

    val scrollState = rememberScrollState()
    val scrollBarAdapter = rememberScrollbarAdapter(scrollState)

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
                        .background(Color(0xff3B5EA1))
//                        .border(0.5.dp, Color.White)
                        .padding(8.dp)
                )
            }
        }
        if (personList.isNullOrEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
                Column(modifier = Modifier.fillMaxWidth().sizeIn(maxHeight = 200.dp), horizontalAlignment = Alignment.CenterHorizontally
                , verticalArrangement = Arrangement.Center) {
                    Text(text = "لاتوجد نتائج للبحث  ", style = CairoTypography.h3)
                    Text(text="يمكنك فلترة بحثك للحصول على نتائج اكثر دقة",style = CairoTypography.h3)
                }
            }
        } else {
        LazyColumn {

            items(personList.chunked(itemsPerPage)[currentPage]) { person ->
                var showPopup = remember { mutableStateOf(false) }
                val showDialog = remember { mutableStateOf(false) }

                var popupPosition by remember { mutableStateOf(IntOffset.Zero) }
//                if (superAdmin) {
//                    ItemMenu(
//                        showMenu = showPopup,
//                        onEdit = {
//
//                            navController.navigate(Screens.AddCoursesScreen(
//                                mode = EDIT,
//                                course = person))
//
//                        },
//                        onRemove = {
//                            onRemoveCourse(person)
//                        },
//                        showDialog = showDialog,
//                        alertText = "هل انت متأكد من أنك تريد مسح هذا الملف ؟"
//                    )
//                }

                Row(
                    modifier = Modifier.background(
                        if (person.procedures["لائق صحيا"] == true) Color.Green.copy(alpha = 0.20f) else
                            if (person.procedures["غير لائق صحيا"] == true) Color.Red.copy(alpha = 0.20f) else
                                if ((currentPage % 2 == 0 && personList.indexOf(person) % 2 == 0) ||
                                    (currentPage % 2 != 0 && personList.indexOf(person) % 2 != 0)
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
//                    if (superAdmin){
//                        ItemMenu(
//                        showMenu = showPopup,
//                        onEdit = {
//
//                            navController.navigate(Screens.AddCoursesScreen(
//                                mode = EDIT,
//                                course = person))
//
//                        },
//                        onRemove = {
//                            onRemoveCourse(person)
//                        },
//                        showDialog = showDialog,
//                        alertText = "هل انت متأكد من أنك تريد مسح هذا الملف ؟"
//                    )
//                    }
                    ItemMenu(
                        showMenu = showPopup,
                        onEdit = {
                            navController.navigate(Screens.AddCoursesScreen(
                                mode = EDIT,
                                course = person))

                        },

                        onRemove = {

                            onRemoveCourse(person)
                            ("تمت عملية مسح الملف بنجاح").showErrorMessage()

                        },

                        showDialog = showDialog,
                        alertText = "هل انت متأكد من أنك تريد مسح هذا الملف ؟"
                    )
                    Text(
                        text = (personList.indexOf(person) + 1).toString(), // display counter value as text
                        maxLines = 1,
                        style = CairoTypography.h3,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(columnWidths[0])
                            .padding(8.dp)
                    )
                    Text(
                        text = person.fileNumber,
                        style = CairoTypography.h3,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(columnWidths[1])
                            .padding(8.dp)
                    )
                    Text(
                        text = person.name,
                        style = CairoTypography.h4,
                        fontWeight = FontWeight.Bold,
//                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(columnWidths[2])
                            .padding(8.dp)
                    )
                    Text(
                        text = person.libyaId,
                        style = CairoTypography.h4,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(columnWidths[3])
                            .padding(8.dp)
                    )
                    Text(
                        text = person.motherName,
                        style = CairoTypography.h4,
                        fontWeight = FontWeight.Bold,
//                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(columnWidths[4])
                            .padding(8.dp)
                    )
                    Text(
                        text = person.educationLevel,
                        style = CairoTypography.h4,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(columnWidths[5])
                            .padding(8.dp)
                    )
                    Text(
                        text = person.city,
                        style = CairoTypography.h4,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(columnWidths[6])
                            .padding(8.dp)
                    )
                    Text(
                        text = person.phoneNumber,
                        style = CairoTypography.h4,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(columnWidths[7])
                            .padding(8.dp)
                    )
                    Text(
                        text = person.recruiter,
                        style = CairoTypography.h4,
                        fontWeight = FontWeight.Bold,
//                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(columnWidths[8])
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
                        modifier = Modifier
                            .width(columnWidths[9])
                            .padding(8.dp)
                    )
                    //val justifications = person.justificationsRequire.filterValues { it }.keys
                    val valueToCheck = person.procedures.get("إحالة لتدريب")

//                    isButtonVisible = person.hasShortfalls()
//                    if (canEditPermission){
//                    if (valueToCheck == false) {
//
//                        Spacer(modifier = Modifier.size(0.dp, 20.dp))
//
//                        common.component.TextButton(
//                            width = columnWidths[10],
//                            text = "إضافة",
//                            onClick = {
//                                navController.navigate(Screens.AddCoursesScreen(mode = EDIT, course = person))
//                            },
//                            colors = AppColors.GreenGradient, cornerRadius = 30.dp
//                        )
//                    }
//                }
//                    else{
//                        Text(
//                            text = "",
//                            modifier = Modifier
//                                .width(columnWidths[10])
//                                .padding(8.dp)
//                        )
//                    }
                    Text(
                        text = person.commission,
                        style = CairoTypography.h4,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(columnWidths[10])
                            .padding(8.dp)
                    )

                    Text(
                        text = if (person.procedures["لائق صحيا"] == true) "لائق" else if (person.procedures["غير لائق صحيا"] == true) "غير لائق" else "",
                        style = CairoTypography.h4,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(columnWidths[11])
                            .padding(8.dp)
                    )
                    Text(
                        text = person.notes,
                        style = CairoTypography.h4,
                        fontWeight = FontWeight.Bold,
//                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(columnWidths[12])
                            .padding(8.dp)
                    )
                    Text(
                        text = if (person.procedures["إحالة لتدريب"] != null &&
                            person.procedures["إحالة لتدريب"] == true) {
                            "نعم"
                        } else {
                            "لا"
                        },
                        style = CairoTypography.h4,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(columnWidths[13])
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
            Spacer(modifier = Modifier.width(650.dp))

            Button(
                shape = RoundedCornerShape(20.dp),
                onClick = { if (currentPage > 0) currentPage-- }) {
                Text("السابقة",
                    style = CairoTypography.h4,
                    fontWeight = FontWeight.Bold

                )
            }
            Text("الصفحة ${currentPage + 1} من $pageCount",
                style = CairoTypography.h4,
                fontWeight = FontWeight.Bold,

                modifier = Modifier.padding(8.dp))
            Button(
                shape = RoundedCornerShape(20.dp),
                onClick = { if (currentPage < pageCount - 1) currentPage++ }) {
                Text("التالية",
                    style = CairoTypography.h4,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}}