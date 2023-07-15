package features.courses.presentation.courses

sealed class PrintEvent {
    data class PrintList(val list: List<String>): PrintEvent()

    data class PrintToDirectory(val path:String):PrintEvent()

    object Submit:PrintEvent()
}