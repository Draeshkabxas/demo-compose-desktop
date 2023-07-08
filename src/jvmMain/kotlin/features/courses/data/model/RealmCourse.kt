package features.courses.data.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import utils.AgeGroup

open class RealmCourse: RealmObject{
    @PrimaryKey
    var id:ObjectId = ObjectId()
    var name: String = ""
    var motherName: String = ""
    var fileNUmber: String = ""
    var libyaId: String = ""
    var phoneNUmber: String = ""
    var educationLevel: String = ""
    var recruiter: String = ""
    var city: String = ""
    var ageGroup:String = AgeGroup.UnderEightTeen.arabicName
    var justificationsRequire: RealmList<JustificationCourse> = realmListOf()
    var procedures: RealmList<ProcedureCourse> = realmListOf()
}

open class JustificationCourse: RealmObject {
    var name: String = ""
    var value: Boolean = false
}

open class ProcedureCourse: RealmObject {
    var name: String = ""
    var value: Boolean = false
}
