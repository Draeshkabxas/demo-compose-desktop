package features.sons_of_officers.data.dto

import features.courses.data.dto.toCourseDTO
import features.sons_of_officers.data.model.RealmPerson
import features.sons_of_officers.domain.model.Person
import utils.fromArabicNameToAgeGroup

fun RealmPerson.toPersonDTO(): Person {
    return Person(
        id=this.id,
        name = this.name,
        motherName = this.motherName,
        fileNumber = this.fileNUmber,
        libyaId = this.libyaId,
        phoneNumber = this.phoneNUmber,
        educationLevel = this.educationLevel,
        recruiter = this.recruiter,
        city = this.city,
        commission = this.commission,
        ageGroup=this.ageGroup.fromArabicNameToAgeGroup(),
        justificationsRequire = this.justificationsRequire.associate { it.name to it.value },
        procedures = this.procedures.associate { it.name to it.value }
    )
}