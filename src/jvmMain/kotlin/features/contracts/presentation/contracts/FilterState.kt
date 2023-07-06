package features.contracts.presentation.contracts

import androidx.compose.ui.text.toLowerCase
import utils.AgeGroup

data class FilterState(
    val libyaId:String = "",
    val fileNumber:String="",
    val educationLevel:String="",
    val city:String="",
    val motherName:String="",
    val personName:String="",
    val ageGroup: AgeGroup = AgeGroup.UnderEightTeen
)