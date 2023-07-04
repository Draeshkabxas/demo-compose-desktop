package features.sons_of_officers.presentation.sons_of_officers

import androidx.compose.ui.text.toLowerCase

data class FilterState(
    val libyaId:String = "",
    val fileNumber:String="",
    val educationLevel:String="",
    val city:String="",
    val fileState:String="",
    val referralForTraining:String="",
){
//    fun getFilterStateVariablesNamesAndValues():Map<String,String>{
//        return mapOf(
//            "libyaId" to libyaId,
//            "fileNumber" to fileNumber,
//            "educationLevel" to educationLevel,
//            "city" to city,
//            "fileState" to fileState,
//            "referralForTraining" to referralForTraining,
//            )
//    }
}