package features.sons_of_officers.data.dto

import features.sons_of_officers.data.model.Justification
import features.sons_of_officers.data.model.Procedure
import features.sons_of_officers.data.model.RealmPerson
import features.sons_of_officers.domain.model.Person
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

fun Person.toRealmPerson(): RealmPerson {
    val person=this
    var justifications = realmListOf<Justification>()
    this.justificationsRequire.forEach { justifications.add(Justification().apply {
        name = it.key
        value = it.value
    }) }

    var procedures = realmListOf<Procedure>()
    this.procedures.forEach { procedures.add(Procedure().apply {
        name = it.key
        value = it.value
    }) }

    return RealmPerson().apply {
        id = ObjectId(person.id)
        name = person.name
        motherName = person.motherName
        fileNUmber = person.fileNUmber
        libyaId = person.libyaId
        phoneNUmber = person.phoneNUmber
        educationLevel = person.educationLevel
        recruiter = person.recruiter
        city = person.city
        justificationsRequire = justifications
        procedures=procedures
    }

}