package common.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import styles.CairoTypography


@Composable
fun Section(
    label: String,
    columns: Int,
    gridContent: LazyGridScope.() -> Unit,
) {
    Column(
        modifier = Modifier.padding(vertical = 0.dp),
    ) {
        Text(
            text = label,
            style = CairoTypography.h2,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            columns = GridCells.Fixed(columns),
            contentPadding = PaddingValues(10.dp)
        ){
            gridContent()
        }
    }
}

@Preview()
@Composable
private fun PreviewSection() {
    Section("hello",3){
        repeat(10){
            item {
                CustomOutlinedTextField(errorMessage = "")
            }
        }
    }
}