package features.results.presentation.results

sealed class FilterEvent {
//    data class FilterLibyaId(val libyaId:String):FilterEvent()
//    data class FilterFileNumber(val fileNumber:String):FilterEvent()
//    data class FilterEducationLevel(val educationLevel:String):FilterEvent()
    data class FilterDate(val date:String):FilterEvent()
//    data class FilterMotherName(val motherName:String):FilterEvent()
    data class FilterName(val personName:String):FilterEvent()
//    data class FilterAgeGroup(val ageGroup:String):FilterEvent()
    object Reset:FilterEvent()
    object Submit:FilterEvent()
}