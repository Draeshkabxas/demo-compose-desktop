package features.sons_of_officers.presentation.sons_of_officers

sealed class PrintEvent {
    data class PrintList(val list: List<String>): PrintEvent()

    data class PrintToDirectory(val path:String):PrintEvent()

    object Submit:PrintEvent()
}