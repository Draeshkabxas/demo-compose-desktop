package common.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import features.sons_of_officers.domain.model.Person
import styles.CairoTypography

@Composable
fun PaginatedTable(
    headers: List<String>,
    personList: List<Person>,
    itemsPerPage: Int,
    columnWidths: List<Dp>
) {
    if (personList.isEmpty()) return
    val pageCount = (personList.size + itemsPerPage - 1) / itemsPerPage
    var currentPage by remember { mutableStateOf(0) }
    //btn check
    var isButtonVisible by remember { mutableStateOf(false) }


    Column() {
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
        if (personList.any { false }) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
                Text(text = "No data")
            }
        } else {
        LazyColumn {
            items(personList.chunked(itemsPerPage)[currentPage]) { person ->
                Row(
                    modifier = Modifier.background(
                        if ((currentPage % 2 == 0 && personList.indexOf(person) % 2 == 0) ||
                            (currentPage % 2 != 0 && personList.indexOf(person) % 2 != 0)
                        )
                            Color.LightGray else Color.White
                    )
                ) {
                    Text(
                        text = person.id,
                        maxLines = 1,
                        modifier = Modifier
                            .width(columnWidths[0])
                            .padding(8.dp)
                    )
                    Text(
                        text = person.fileNumber,
                        modifier = Modifier
                            .width(columnWidths[1])
                            .padding(8.dp)
                    )
                    Text(
                        text = person.name,
                        modifier = Modifier
                            .width(columnWidths[2])
                            .padding(8.dp)
                    )
                    Text(
                        text = person.libyaId,
                        modifier = Modifier
                            .width(columnWidths[3])
                            .padding(8.dp)
                    )
                    Text(
                        text = person.motherName,
                        modifier = Modifier
                            .width(columnWidths[4])
                            .padding(8.dp)
                    )
                    Text(
                        text = person.educationLevel,
                        modifier = Modifier
                            .width(columnWidths[5])
                            .padding(8.dp)
                    )
                    Text(
                        text = person.city,
                        modifier = Modifier
                            .width(columnWidths[6])
                            .padding(8.dp)
                    )
                    Text(
                        text = person.phoneNumber,
                        modifier = Modifier
                            .width(columnWidths[7])
                            .padding(8.dp)
                    )
                    Text(
                        text = person.recruiter,
                        modifier = Modifier
                            .width(columnWidths[8])
                            .padding(8.dp)
                    )
                    Text(
                        text = if (person.procedures["إحالة لتدريب"] != null &&
                            person.procedures["إحالة لتدريب"] == true) {
                            "نعم"
                        } else {
                            "لا"
                        },
                        modifier = Modifier
                            .width(columnWidths[9])
                            .padding(8.dp)
                    )
                    Text(
                        text = if (person.justificationsRequire.values.all { it }) {
                            "مستوفي"
                        } else {
                            "نواقص"
                        },
                        modifier = Modifier
                            .width(columnWidths[10])
                            .padding(8.dp)
                    )
                    //val justifications = person.justificationsRequire.filterValues { it }.keys
                    isButtonVisible =
                        !person.justificationsRequire.values.all { it } && !person.procedures.values.all { it }
                    if (isButtonVisible) {
                        Button(
                            onClick = { /* handle button click */ }
                        ) {
                            Text("إضافة")
                        }
                    }
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
}