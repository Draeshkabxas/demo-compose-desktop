package features.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.himanshoe.charty.common.ChartDataCollection
import com.himanshoe.charty.common.config.StartAngle
//import com.himanshoe.charty.pie.PieChart
import com.himanshoe.charty.pie.config.PieChartDefaults
import com.himanshoe.charty.pie.model.PieData
import navcontroller.NavController
import common.component.SystemScreen


@Composable
fun HomeScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(navController.currentScreen.value)
        Button(
            onClick = {
                navController.navigate(SystemScreen.AddSonsOfOfficersScreen.name)
            }) {
            Text("Navigate to Add Sons")
        }
//        PieChart(
//            pieChartConfig = PieChartDefaults.defaultConfig().copy(donut = false),
//            dataCollection = ChartDataCollection(data),
//            modifier = Modifier.wrapContentSize()
//        )
//        PieChart(
//            dataCollection = ChartDataCollection(data),
//            modifier = Modifier.wrapContentSize()
//        )
    }


}





//private val data = listOf(
//    PieData(30f, "Category A", color = Color(0xffed625d)),
//    PieData(20f, "Category B", color = Color(0xfff79f88)),
//    PieData(13f, "Category C", color = Color(0xFF43A047)),
//    PieData(10f, "Category D", color = Color(0xFF93A047)),
//)
//data class PieChartConfig(
//    val donut: Boolean,
//    val showLabel: Boolean,
//    val startAngle: StartAngle = StartAngle.Zero
//)