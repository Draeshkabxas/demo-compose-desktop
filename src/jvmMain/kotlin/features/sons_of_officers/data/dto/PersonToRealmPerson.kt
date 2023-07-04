package features.sons_of_officers.data.dto

import features.sons_of_officers.data.model.Justification
import features.sons_of_officers.data.model.Procedure
import features.sons_of_officers.data.model.RealmPerson
import features.sons_of_officers.domain.model.Person
import io.realm.kotlin.ext.realmListOf
import org.mongodb.kbson.ObjectId

fun Person.toRealmPerson(): RealmPerson {
    val person=this
    var justifications = realmListOf<Justification>()
    this.justificationsRequire.forEach { justifications.add(Justification().apply {
        name = it.key
        value = it.value
    }) }

    var proceduresRealm = realmListOf<Procedure>()
    this.procedures.forEach { proceduresRealm.add(Procedure().apply {
        name = it.key
        value = it.value
    }) }

    return RealmPerson().apply {
        id = if (person.id.isNotEmpty()) ObjectId(person.id) else ObjectId()
        name = person.name
        motherName = person.motherName
        fileNUmber = person.fileNumber
        libyaId = person.libyaId
        phoneNUmber = person.phoneNumber
        educationLevel = person.educationLevel
        recruiter = person.recruiter
        city = person.city
        justificationsRequire = justifications
        procedures=proceduresRealm
    }

}