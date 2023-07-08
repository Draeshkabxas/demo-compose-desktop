package features.contracts.presentation.contracts.component

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import features.contracts.domain.model.Contract
import styles.AppColors
import styles.AppColors.blue
import styles.CairoTypography

@Composable
fun PaginatedTable(
    headers: List<String>,
    contractList: List<Contract>,
    itemsPerPage: Int,
    columnWidths: List<Dp>
) {
//    if (contractList.isEmpty()) return
    val pageCount = (contractList.size + itemsPerPage - 1) / itemsPerPage
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
                        .background(blue)
//                        .border(0.5.dp, Color.White)
                        .padding(8.dp)
                )
            }
        }
        if (contractList.isNullOrEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
                Column(modifier = Modifier.fillMaxWidth().sizeIn(maxHeight = 200.dp), horizontalAlignment = Alignment.CenterHorizontally
                    , verticalArrangement = Arrangement.Center) {
                    Text(text = "لاتوجد نتائج للبحث  ", style = CairoTypography.h3)
                    Text(text="يمكنك فلترة بحثك للحصول على نتائج اكثر دقة",style = CairoTypography.h3)
                }
            }
        } else {
            var counter = 0 // initialize counter based on current page and items per page
            LazyColumn {
                items(contractList.chunked(itemsPerPage)[currentPage]) { contract ->
                    Row(
                        modifier = Modifier.background(
                            if ((currentPage % 2 == 0 && contractList.indexOf(contract) % 2 == 0) ||
                                (currentPage % 2 != 0 && contractList.indexOf(contract) % 2 != 0)
                            )
                                Color.LightGray else Color.White
                        )
                    ) {
                        Text(
                            text = (contractList.indexOf(contract) + 1).toString(), // display counter value as text
                            maxLines = 1,
                            style = CairoTypography.h3,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(columnWidths[0])
                                .padding(8.dp)
                        )
                        Text(
                            text = contract.fileNumber,
                            style = CairoTypography.h3,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(columnWidths[1])
                                .padding(8.dp)
                        )
                        Text(
                            text = contract.name,
                            style = CairoTypography.h4,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(columnWidths[2])
                                .padding(8.dp)
                        )
                        Text(
                            text = contract.libyaId,
                            style = CairoTypography.h4,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(columnWidths[3])
                                .padding(8.dp)
                        )
                        Text(
                            text = contract.motherName,
                            style = CairoTypography.h4,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(columnWidths[4])
                                .padding(8.dp)
                        )
                        Text(
                            text = contract.motherNationality,
                            style = CairoTypography.h3,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(columnWidths[5])
                                .padding(8.dp)
                        )
                        Text(
                            text = contract.educationLevel,
                            style = CairoTypography.h4,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(columnWidths[6])
                                .padding(8.dp)
                        )
                        Text(
                            text = contract.city,
                            style = CairoTypography.h4,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(columnWidths[7])
                                .padding(8.dp)
                        )
                        Text(
                            text = contract.phoneNumber,
                            style = CairoTypography.h4,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(columnWidths[8])
                                .padding(8.dp)
                        )
                        Text(
                            text = contract.dependency,
                            style = CairoTypography.h4,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(columnWidths[9])
                                .padding(8.dp)
                        )
                        Text(
                            text = contract.bankName,
                            style = CairoTypography.h4,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(columnWidths[10])
                                .padding(8.dp)
                        )
                        Text(
                            text = contract.accountNumber,
                            style = CairoTypography.h4,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(columnWidths[11])
                                .padding(8.dp)
                        )
                        Text(
                            text = contract.notes,
                            style = CairoTypography.h4,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(columnWidths[11])
                                .padding(8.dp),
                            maxLines = 2
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
            Button(
                shape = RoundedCornerShape(20.dp),
                onClick = { if (currentPage > 0) currentPage-- }) {
                Text(
                    "السابقة",
                    style = CairoTypography.h4,
                    fontWeight = FontWeight.Bold

                )
            }
            Text(
                "الصفحة ${currentPage + 1} من $pageCount",
                style = CairoTypography.h4,
                fontWeight = FontWeight.Bold,

                modifier = Modifier.padding(8.dp)
            )
            Button(
                shape = RoundedCornerShape(20.dp),
                onClick = { if (currentPage < pageCount - 1) currentPage++ }) {
                Text(
                    "التالية",
                    style = CairoTypography.h4,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}