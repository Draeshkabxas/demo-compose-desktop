package features.sons_of_officers.presentation.sons_of_officers

import androidx.compose.ui.text.toLowerCase
import utils.AgeGroup
data class FilterState(
    val libyaId:String = "",
    val fileNumber:String="",
    val educationLevel:String="",
    val city:String="",
    val fileState:String="",
    val referralForTraining:String="",
    val ageGroup: AgeGroup? = null,
    val healthStatus:String="",
)