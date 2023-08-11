package features.results.presentation.results

sealed class PrintEvent {
    data class PrintList(val list: List<String>): PrintEvent()

    data class PrintToDirectory(val path:String):PrintEvent()

    object Submit:PrintEvent()
}