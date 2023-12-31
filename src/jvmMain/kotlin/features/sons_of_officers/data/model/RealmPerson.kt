package features.sons_of_officers.data.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import utils.AgeGroup

open class RealmPerson: RealmObject{
    @PrimaryKey
    var id:String = ObjectId().toHexString()
    var name: String = ""
    var motherName: String = ""
    var fileNUmber: String = ""
    var libyaId: String = ""
    var phoneNUmber: String = ""
    var educationLevel: String = ""
    var recruiter: String = ""
    var city: String = ""
    var commission :String =""
    var notes : String =""

    var ageGroup:String = AgeGroup.UnderEightTeen.arabicName
    var justificationsRequire: RealmList<Justification> = realmListOf()
    var procedures: RealmList<Procedure> = realmListOf()
}

open class Justification: RealmObject {
    var name: String = ""
    var value: Boolean = false
}

open class Procedure: RealmObject {
    var name: String = ""
    var value: Boolean = false
}
