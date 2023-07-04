package common.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp

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
    cellContent: @Composable (columnIndex: Int, rowIndex: Int) -> Unit
) {
    val rowCount = data.size
    val columnCount = headers.size
    val columnWidths = remember { mutableStateMapOf<Int, Int>() }

    // Add a border to the table
    Box(
        modifier = modifier.border(1.dp, Color(0xff3B5EA1))
            .then(Modifier.horizontalScroll(horizontalScrollState))
    ) {
        Column {
            Row(modifier = rowModifier) {
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
                            .background(Color(0xff3B5EA1))
                    ) {
                        Text(
                            text = header,
                            style = MaterialTheme.typography.h5,
                            color = Color.White,
                            modifier = Modifier.padding(13.dp)
                        )
                    }
                }
            }

            LazyColumn(state = verticalLazyListState) {
                items(count = rowCount, key = { rowIndex -> rowIndex }) { rowIndex ->
                    // Add a border to every row
                    Row(
                        modifier = rowModifier.border(
                            1.dp, Color(0xff3B5EA1)
                        ).background(Color.White),
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
    }
}