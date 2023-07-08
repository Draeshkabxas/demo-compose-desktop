package features.courses.presentation.add_courses

data class CourseInfoFormState(
    val name: String = "",
    val nameError: String? = null,
    val motherName: String = "",
    val motherNameError: String? = null,
    val fileNumber: String = "",
    val fileNumberError: String? = null,
    val libyaid: String = "",
    val libyaidError: String? = null,
    val phoneNumber: String = "",
    val phoneNumberError: String? = null,
    val educationLevel: String = "",
    val educationLevelError: String? = null,
    val recruiter: String = "",
    val recruiterError: String? = null,
    val city: String = "",
    val cityError: String? = null,
)
