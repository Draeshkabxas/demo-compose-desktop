package features.courses.presentation.add_courses

import common.component.ScreenMode

sealed class CourseInfoFormEvent {
    data class NameChanged(val name: String) : CourseInfoFormEvent()
    data class MotherNameChanged(val name: String) : CourseInfoFormEvent()
    data class FileNumberChanged(
        val fileNumber: String
    ) : CourseInfoFormEvent()

    data class LibyaIdChanged(val libyaId: String) : CourseInfoFormEvent()
    data class PhoneNumberChanged(val phone: String) : CourseInfoFormEvent()
    data class EducationLevelChanged(val educationLevel: String) : CourseInfoFormEvent()

    data class RecruiterChanged(val recruiter: String) : CourseInfoFormEvent()
    data class CityChanged(val city: String) : CourseInfoFormEvent()
    data class CommissionChanged(val commission: String) : CourseInfoFormEvent()
    data class NotesChanged(val notes: String) : CourseInfoFormEvent()



    data class Submit(val mode: ScreenMode = ScreenMode.ADD): CourseInfoFormEvent()
}
