package features.home.data.repository

import features.home.domain.repository.BackupRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.core.component.getScopeId
import org.koin.core.context.GlobalContext.get
import org.koin.dsl.module
import realmdb.RealmWrapper
import java.io.IOException
class BackupRealmDBImpl(private var realm: Realm) : BackupRepository {

    override fun saveBackup(dirPath: String) {
        val backUpConfig = RealmConfiguration.Builder(realm.configuration.schema)
            .directory(dirPath)
            .build()
        try {
            realm.writeCopyTo(backUpConfig)
        } catch (e: IOException) {
            e.printStackTrace()
            // Handle error here
        }
    }

    override fun getBackup(dirPath: String) {
        val backUpConfig = RealmConfiguration.Builder(realm.configuration.schema)
            .directory(dirPath)
            .schemaVersion(realm.schemaVersion())
            .build()
        try {
            val backUpRealm = Realm.open(backUpConfig)
            val currentRealmConfig = realm.configuration
            realm.close()
            Realm.deleteRealm(currentRealmConfig)
            val newRealmConfig =RealmConfiguration.Builder(realm.configuration.schema)
                .schemaVersion(realm.schemaVersion())
                .build()
            backUpRealm.writeCopyTo(newRealmConfig)
            get().get<RealmWrapper>().realm = Realm.open(newRealmConfig)
            println("new realm is set")

            backUpRealm.close()
        } catch (e: IOException) {
            e.printStackTrace()
            // Handle error here
        }

//        val backupFile = File(filePath)
//
//        if (backupFile.exists()) {
//            try {
//                FileInputStream(backupFile).use { inputStream ->
//                    FileOutputStream(realm.path).use { outputStream ->
//                        val buf = ByteArray(1024)
//                        var bytesRead: Int
//                        while (inputStream.read(buf).also { bytesRead = it } > 0) {
//                            outputStream.write(buf, 0, bytesRead)
//                        }
//                    }
//                }
//            } catch (e: IOException) {
//                e.printStackTrace()
//                // Handle error here
//            }
//        } else {
//            // Handle error here
//        }
    }
}