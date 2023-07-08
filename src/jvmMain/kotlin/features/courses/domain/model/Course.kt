package features.courses.domain.model

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
