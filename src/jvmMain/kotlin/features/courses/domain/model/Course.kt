package features.courses.domain.model

import features.sons_of_officers.domain.model.Person
import utils.AgeGroup

data class Course(
    val id: String,
    val name: String,
    val motherName: String,
    val fileNumber: String,
    val libyaId: String,
    val phoneNumber: String,
    val educationLevel: String,
    val recruiter: String,
    val city: String,
    val ageGroup: AgeGroup,
    val justificationsRequire: Map<String, Boolean>,
    val procedures: Map<String, Boolean>,
)
fun Course.hasShortfalls():Boolean =
    justificationsRequire.values.any { !it } || procedures.values.any { !it }

//fun Course.result():Boolean = this.procedures["لائق صحيا"] == true
fun Course.result1():Boolean = this.procedures["لائق صحيا"] == true
fun Course.result2():Boolean = this.procedures["غير لائق صحيا"] == true