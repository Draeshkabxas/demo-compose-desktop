package features.contracts.presentation.contracts

sealed class FilterEvent {
    data class FilterLibyaId(val libyaId:String):FilterEvent()
    data class FilterFileNumber(val fileNumber:String):FilterEvent()
    data class FilterEducationLevel(val educationLevel:String):FilterEvent()
    data class FilterCity(val city:String):FilterEvent()
    data class FilterMotherName(val motherName:String):FilterEvent()
    data class FilterReferralForTraining(val personName:String):FilterEvent()
    object Reset:FilterEvent()
    object Submit:FilterEvent()
}