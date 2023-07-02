package features.sons_of_officers.presentation

sealed class PersonalInfoFormEvent {
    data class NameChanged(val name: String) : PersonalInfoFormEvent()
    data class MotherNameChanged(val name: String) : PersonalInfoFormEvent()
    data class FileNumberChanged(
        val fileNumber: String
    ) : PersonalInfoFormEvent()

    data class LibyaIdChanged(val libyaId: String) : PersonalInfoFormEvent()
    data class PhoneNumberChanged(val phone: String) : PersonalInfoFormEvent()
    data class EducationLevelChanged(val educationLevel: String) : PersonalInfoFormEvent()

    data class RecruiterChanged(val recruiter: String) : PersonalInfoFormEvent()
    data class CityChanged(val city: String) : PersonalInfoFormEvent()

    object Submit: PersonalInfoFormEvent()
}
