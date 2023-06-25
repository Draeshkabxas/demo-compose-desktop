package authorization.data.repository

import authorization.domain.repository.AppCloseRepository
import kotlin.system.exitProcess


class AppCloseImpl : AppCloseRepository {
    private val onCloseList = mutableListOf<() -> Unit>()
    override fun close() {
        onCloseList.forEach {
            it.invoke()
        }
        exitProcess(0)
    }

    override fun addToCloseList(onClose: () -> Unit) {
        onCloseList.add(onClose)
    }


}