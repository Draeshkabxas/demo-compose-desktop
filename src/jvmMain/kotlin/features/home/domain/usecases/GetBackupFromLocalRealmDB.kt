package features.home.domain.usecases

import features.home.domain.repository.BackupRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GetBackupFromLocalRealmDB(
    private val backupRepo:BackupRepository
) {
    operator fun invoke(dirPath:String){
        CoroutineScope(Dispatchers.IO).launch {
            backupRepo.getBackup(dirPath)
        }
    }
}