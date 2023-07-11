package features.courses.data.dto

import features.courses.data.model.RealmCourse
import features.courses.domain.model.Course
import features.sons_of_officers.domain.model.Person
import utils.fromArabicNameToAgeGroup

fun RealmCourse.toCourseDTO(): Course {
    return Course(
        id=this.id,
        name = this.name,
        motherName = this.motherName,
        fileNumber = this.fileNUmber,
        libyaId = this.libyaId,
        phoneNumber = this.phoneNUmber,
        educationLevel = this.educationLevel,
        recruiter = this.recruiter,
        city = this.city,
        ageGroup=this.ageGroup.fromArabicNameToAgeGroup(),
        justificationsRequire = this.justificationsRequire.associate { it.name to it.value },
        procedures = this.procedures.associate { it.name to it.value }
    )
}