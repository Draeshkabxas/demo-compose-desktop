package features.contracts.presentation.contracts

sealed class PrintEvent {
    data class PrintList(val list: List<String>): PrintEvent()

    data class PrintToDirectory(val path:String):PrintEvent()

    object Submit:PrintEvent()
}