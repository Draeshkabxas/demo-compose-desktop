package realmdb


import io.realm.kotlin.dynamic.DynamicMutableRealm
import io.realm.kotlin.dynamic.DynamicMutableRealmObject
import io.realm.kotlin.dynamic.DynamicRealm
import io.realm.kotlin.dynamic.DynamicRealmObject
import io.realm.kotlin.migration.AutomaticSchemaMigration
import org.mongodb.kbson.ObjectId

fun firstRealmMigrate(): AutomaticSchemaMigration = AutomaticSchemaMigration { context ->
    val oldRealm: DynamicRealm = context.oldRealm
    val newRealm: DynamicMutableRealm = context.newRealm

    val currentVersion = oldRealm.schemaVersion()
    if (currentVersion == 0L) {
        context.enumerate("RealmContract") { oldObject: DynamicRealmObject, newObject: DynamicMutableRealmObject? ->
            val oldId = oldObject.getValue("id", String::class)
            newObject?.set("id", if (oldId.isEmpty()) ObjectId() else ObjectId(oldId))
        }
    }else if(currentVersion == 1L){
        context.enumerate("RealmPerson") { oldObject: DynamicRealmObject, newObject: DynamicMutableRealmObject? ->
            newObject?.set("ageGroup", "")
        }
    }

}
