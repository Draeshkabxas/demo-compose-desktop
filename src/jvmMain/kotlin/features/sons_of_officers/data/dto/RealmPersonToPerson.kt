package features.sons_of_officers.data.dto

import features.sons_of_officers.data.model.RealmPerson
import features.sons_of_officers.domain.model.Person

fun RealmPerson.toPersonDTO(): Person {
    return Person(
        id=this.id.toHexString(),
        name = this.name,
        motherName = this.motherName,
        fileNumber = this.fileNUmber,
        libyaId = this.libyaId,
        phoneNumber = this.phoneNUmber,
        educationLevel = this.educationLevel,
        recruiter = this.recruiter,
        city = this.city,
        justificationsRequire = this.justificationsRequire.associate { it.name to it.value },
        procedures = this.procedures.associate { it.name to it.value }
    )
}