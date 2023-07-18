package features.home.domain.repository

interface BackupRepository {
    fun saveBackup(dirPath:String)

    fun getBackup(dirPath:String)
}