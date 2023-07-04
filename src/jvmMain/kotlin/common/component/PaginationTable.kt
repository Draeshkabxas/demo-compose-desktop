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
import styles.CairoTypography

@Composable
fun PaginatedTable(
    headers: List<String>,
    data: List<List<String>>,
    itemsPerPage: Int,
    columnWidths: List<Dp>
) {
    val pageCount = (data.size + itemsPerPage - 1) / itemsPerPage
    var currentPage by remember { mutableStateOf(0) }

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
        LazyColumn {
            items(data.chunked(itemsPerPage)[currentPage]) { row ->
                Row(
                    modifier = Modifier.background(
                        if ((currentPage % 2 == 0 && data.indexOf(row) % 2 == 0) ||
                            (currentPage % 2 != 0 && data.indexOf(row) % 2 != 0))
                         Color.LightGray else Color.White
                    )
                ) {
                    row.forEachIndexed { index, cell ->
                        Text(
                            text = cell,
                            modifier = Modifier
                                .width(columnWidths[index])
                                .padding(8.dp)
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
            Button(        shape = RoundedCornerShape(20.dp),
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
            Button(        shape = RoundedCornerShape(20.dp),
                onClick = { if (currentPage < pageCount - 1) currentPage++ }) {
                Text("التالية",
                        style = CairoTypography.h4,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
