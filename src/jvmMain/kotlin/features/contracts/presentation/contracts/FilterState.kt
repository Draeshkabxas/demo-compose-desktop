package features.contracts.presentation.contracts

import androidx.compose.ui.text.toLowerCase

data class FilterState(
    val libyaId:String = "",
    val fileNumber:String="",
    val educationLevel:String="",
    val city:String="",
    val motherName:String="",
    val personName:String="",
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