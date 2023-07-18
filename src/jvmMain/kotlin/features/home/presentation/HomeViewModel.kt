package features.home.presentation

import features.home.domain.usecases.GetBackupFromLocalRealmDB
import features.home.domain.usecases.SaveBackupOfRealmInDirectory

class HomeViewModel (
private val saveBackupOfRealmInDirectory: SaveBackupOfRealmInDirectory,
    private val getBackupFromLocalRealmDB: GetBackupFromLocalRealmDB
) {


     fun saveBackupTheDB(
         dirPath: String,
         onError: (String) -> Unit,
         onLoading: () -> Unit,
         onSuccess: (Boolean) -> Unit
    ) {
         saveBackupOfRealmInDirectory(dirPath)
    }


    fun getBackup(dirPath: String){
        getBackupFromLocalRealmDB(dirPath)
    }


}

