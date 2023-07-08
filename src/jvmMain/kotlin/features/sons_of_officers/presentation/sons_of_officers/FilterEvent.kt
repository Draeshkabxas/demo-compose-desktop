package features.sons_of_officers.presentation.sons_of_officers
sealed class FilterEvent {
    data class FilterLibyaId(val libyaId:String):FilterEvent()
    data class FilterFileNumber(val fileNumber:String):FilterEvent()
    data class FilterEducationLevel(val educationLevel:String):FilterEvent()
    data class FilterCity(val city:String):FilterEvent()
    data class FilterFileState(val fileState:Boolean):FilterEvent()
    data class FilterReferralForTraining(val referralForTraining:Boolean):FilterEvent()
    data class FilterAgeGroup(val ageGroup:String): FilterEvent()
    data class FilterHealthStatus(val healthStatus: String): FilterEvent()

    object Reset:FilterEvent()
    object Submit:FilterEvent()
}