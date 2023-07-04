package common.component.simpledatatable

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import common.toColor
import styles.AppColors.primary
import styles.CairoTypography

@Composable
fun SimpleDataTableScreen(
    headers: List<String>,
    data: List<List<String>>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
    ) {
        LazyColumn(Modifier.padding(16.dp)) {
            item {
                Box(modifier = Modifier.size(1400.dp).fillMaxWidth()) {
                    Table(
                        modifier = Modifier.fillMaxSize(),
                        headers = headers,
                        data = data,
                        afterHeader = {
                              Box(modifier = Modifier.fillMaxWidth().border(10.dp, primary))
                        },
                        cellContent = { columnIndex, rowIndex ->
                            Text(
                                text = data[rowIndex][columnIndex],
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(8.dp)
                            )
                        })
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Table(
    modifier: Modifier = Modifier,
    rowModifier: Modifier = Modifier,
    verticalLazyListState: LazyListState = rememberLazyListState(),
    horizontalScrollState: ScrollState = rememberScrollState(),
    headers: List<String>,
    data: List<List<String>>,
    beforeRow: (@Composable (rowIndex: Int) -> Unit)? = null,
    afterRow: (@Composable (rowIndex: Int) -> Unit)? = null,
    beforeHeader: (@Composable () -> Unit)? = null,
    afterHeader: (@Composable () -> Unit)? = null,
    cellContent: @Composable (columnIndex: Int, rowIndex: Int) -> Unit
) {
    val rowCount = data.size
    val columnCount = headers.size
    val columnWidths = remember { mutableStateMapOf<Int, Int>() }

    // Add a border to the table
    LazyColumn(state = verticalLazyListState) {
        item { beforeHeader?.invoke() }
        item {
            TableHeader(
                modifier,
                headers = headers,
                columnWidths = columnWidths,
            ) { _, headerText ->
                Text(
                    text = headerText,
                    style = CairoTypography.body1.copy(color = primary),
                    modifier = Modifier.padding(13.dp)
                )
            }
        }
        item { afterHeader?.invoke() }
        items(count = rowCount, key = { rowIndex -> rowIndex }) { rowIndex ->
            // Add a border to every row
            val interactionSource = remember { MutableInteractionSource() }
            val isHovered = interactionSource.collectIsHoveredAsState().value

            Row(
                modifier = rowModifier
                    .background(if (isHovered) Color.Gray else Color.White)
                    .hoverable(interactionSource = interactionSource, enabled = true)
            ) {
                data[rowIndex].forEachIndexed { columnIndex, cellData ->
                    Box(
                        modifier = Modifier
                            .layout { measurable, constraints ->
                                val placeable = measurable.measure(constraints)

                                val existingWidth = columnWidths[columnIndex] ?: 0
                                val maxWidth = maxOf(existingWidth, placeable.width)

                                if (maxWidth > existingWidth) {
                                    columnWidths[columnIndex] = maxWidth
                                }

                                layout(width = maxWidth, height = placeable.height) {
                                    placeable.placeRelative(0, 0)
                                }
                            }
                    ) {
                        cellContent(columnIndex, rowIndex)
                    }
                }
            }
        }
    }
}

@Composable
fun TableHeader(
    modifier: Modifier,
    headers: List<String>,
    color: Color = primary,
    columnWidths: SnapshotStateMap<Int, Int>,
    cellContent: @Composable() (index: Int, headerText: String) -> Unit
) {
    Row(modifier = modifier) {
        headers.forEachIndexed { columnIndex, header ->
            Box(
                modifier = Modifier
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)

                        val existingWidth = columnWidths[columnIndex] ?: 0
                        val maxWidth = maxOf(existingWidth, placeable.width)

                        if (maxWidth > existingWidth) {
                            columnWidths[columnIndex] = maxWidth
                        }

                        layout(width = maxWidth, height = placeable.height) {
                            placeable.placeRelative(0, 0)
                        }
                    }
            ) {
                cellContent(columnIndex, header)
            }
        }
    }
}

@Composable
fun Divider(
    color: Color = Color.Black,
    thickness: Dp = 1.dp,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxWidth()) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = thickness.value
        )
    }
}


@Composable
fun SimpleDataTableScreenPreview() {
    val headers = listOf(
        "التسلسل", "رقم الملف", "الإسم رباعي", "الرقم الوطني", "إسم الأم", "المؤهل العلمي", "المدينة", "رقم الهاتف",
        "القائم بالتجنيد", "إحالة لتدريب", "حالة الملف", "النواقص"
    )

    val data = listOf(
        listOf(
            "1",
            "222",
            " احمد محمد احمد محمود",
            "1199911111111",
            "عائشة محمد عبدالله",
            "بكالوريوس",
            "طرابلس",
            "0910000000",
            "احمد محمد احمد",
            "لا",
            "غير مستوفي",
            "إضافة"
        ),
        listOf("2", "Jane", "Doe", "30", "Female", "Canada", "MSc", "Toronto", "Married", "No", "$2000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        listOf("3", "Bob", "Smith", "40", "Male", "UK", "PhD", "London", "Married", "Yes", "$3000", "ediet"),
        //

        // Add more rows here...
    )
    SimpleDataTableScreen(headers = headers, data)
}

