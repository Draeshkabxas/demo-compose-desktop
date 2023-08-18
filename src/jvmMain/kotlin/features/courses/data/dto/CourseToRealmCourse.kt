package features.courses.data.dto

import features.courses.data.model.JustificationCourse
import features.courses.data.model.ProcedureCourse
import features.courses.data.model.RealmCourse
import features.courses.domain.model.Course
import features.sons_of_officers.domain.model.Person
import io.realm.kotlin.ext.realmListOf
import org.mongodb.kbson.ObjectId

fun Course.toRealmCourse(): RealmCourse {
    val person=this
    val justifications = realmListOf<JustificationCourse>()
    this.justificationsRequire.forEach { justifications.add(JustificationCourse().apply {
        name = it.key
        value = it.value
    }) }

    val proceduresRealm = realmListOf<ProcedureCourse>()
    this.procedures.forEach { proceduresRealm.add(ProcedureCourse().apply {
        name = it.key
        value = it.value
    }) }

    return RealmCourse().apply {
        id = person.id.ifEmpty { ObjectId().toHexString() }
        name = person.name
        motherName = person.motherName
        fileNUmber = person.fileNumber
        libyaId = person.libyaId
        phoneNUmber = person.phoneNumber
        educationLevel = person.educationLevel
        recruiter = person.recruiter
        city = person.city
        ageGroup = person.ageGroup.arabicName
        justificationsRequire = justifications
        procedures=proceduresRealm
        commission =person.commission
        notes=person.notes
    }

}