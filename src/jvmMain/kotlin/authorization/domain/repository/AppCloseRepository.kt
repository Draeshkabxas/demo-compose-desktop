package authorization.domain.repository

interface AppCloseRepository {
    fun close()
    fun addToCloseList(onClose:()->Unit)
}