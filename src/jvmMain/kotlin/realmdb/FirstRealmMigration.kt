package realmdb


import io.realm.kotlin.dynamic.DynamicMutableRealm
import io.realm.kotlin.dynamic.DynamicMutableRealmObject
import io.realm.kotlin.dynamic.DynamicRealm
import io.realm.kotlin.dynamic.DynamicRealmObject
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.migration.AutomaticSchemaMigration
import org.mongodb.kbson.ObjectId

fun firstRealmMigrate(): AutomaticSchemaMigration = AutomaticSchemaMigration { context ->
    val oldRealm: DynamicRealm = context.oldRealm
    val newRealm: DynamicMutableRealm = context.newRealm

    when (oldRealm.schemaVersion()) {
        0L -> {
            if (oldRealm.schema()["RealmContract"]?.isEmbedded == true) {
                context.enumerate("RealmContract") { oldObject: DynamicRealmObject, newObject: DynamicMutableRealmObject? ->
                    val oldId = oldObject.getValue("id", String::class)
                    newObject?.set("id", if (oldId.isEmpty()) ObjectId() else ObjectId(oldId))
                }
            }
        }
        1L -> {
            if (oldRealm.schema()["RealmPerson"]?.isEmbedded == true) {
                context.enumerate("RealmPerson") { oldObject: DynamicRealmObject, newObject: DynamicMutableRealmObject? ->
                    newObject?.set("ageGroup", "")
                }
            }
        }
        2L -> {
            if (oldRealm.schema()["RealmPerson"]?.isEmbedded == true) {
                context.enumerate("RealmPerson") { oldObject: DynamicRealmObject, newObject: DynamicMutableRealmObject? ->
                    val oldId = oldObject.getValue("id", ObjectId::class)
                    newObject?.set("id", oldId.toHexString())
                }
            }
        }
        3L -> {
            if (oldRealm.schema()["RealmCourse"]?.isEmbedded == true) {
                context.enumerate("RealmCourse") { oldObject: DynamicRealmObject, newObject: DynamicMutableRealmObject? ->
                    val oldId = oldObject.getValue("id", ObjectId::class)
                    newObject?.set("id", oldId.toHexString())
                }
            }
        }
        4L -> {
            if (oldRealm.schema()["RealmContract"]?.isEmbedded == true) {
                context.enumerate("RealmContract") { oldObject: DynamicRealmObject, newObject: DynamicMutableRealmObject? ->
                    val oldId = oldObject.getValue("id", ObjectId::class)
                    newObject?.set("id", oldId.toHexString())
                }
            }
        }
        5L -> {
            if (oldRealm.schema()["UsersRealm"]?.isEmbedded == true) {
                context.enumerate("UsersRealm") { oldObject: DynamicRealmObject, newObject: DynamicMutableRealmObject? ->
                    val oldSystemValue = oldObject.getValue("systems", String::class)
                    newObject?.set("systems", realmListOf(oldSystemValue))
                }
            }
        }
    }

}
