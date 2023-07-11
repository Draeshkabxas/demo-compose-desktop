package features.courses.presentation.add_courses

import features.courses.domain.model.Course

data class CourseInfoFormState(
    val id:String ="",
    val name: String = "",
    val nameError: String? = null,
    val motherName: String = "",
    val motherNameError: String? = null,
    val fileNumber: String = "",
    val fileNumberError: String? = null,
    val libyaId: String = "",
    val libyaIdError: String? = null,
    val phoneNumber: String = "",
    val phoneNumberError: String? = null,
    val educationLevel: String = "",
    val educationLevelError: String? = null,
    val recruiter: String = "",
    val recruiterError: String? = null,
    val city: String = "",
    val cityError: String? = null,
)
fun Course.toCourseInfoFormState(): CourseInfoFormState =
    CourseInfoFormState(
        id = this.id,
        name = this.name,
        motherName = this.motherName,
        fileNumber = this.fileNumber,
        libyaId = this.libyaId,
        phoneNumber = this.phoneNumber,
        educationLevel = this.educationLevel,
        recruiter = this.recruiter,
        city = this.city
    )
