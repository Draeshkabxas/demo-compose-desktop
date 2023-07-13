package features.contracts.presentation.add_contracts


import common.component.ScreenMode
import features.courses.presentation.add_courses.CourseInfoFormEvent

sealed class ContractInfoFormEvent {
    data class NameChanged(val name: String) : ContractInfoFormEvent()
    data class MotherNameChanged(val name: String) : ContractInfoFormEvent()
    data class MotherNationalityChanged(val name: String) : ContractInfoFormEvent()
    data class FileNumberChanged(
        val fileNumber: String
    ) : ContractInfoFormEvent()

    data class LibyaIdChanged(val libyaId: String) : ContractInfoFormEvent()
    data class PhoneNumberChanged(val phone: String) : ContractInfoFormEvent()
    data class EducationLevelChanged(val educationLevel: String) : ContractInfoFormEvent()
    data class BankNameChanged(val bankName: String) : ContractInfoFormEvent()
    data class AccountNumberChanged(val accountNumber: String) : ContractInfoFormEvent()

    data class DependencyChanged(val dependency: String) : ContractInfoFormEvent()
    data class CityChanged(val city: String) : ContractInfoFormEvent()
    data class NotesChanged(val notes: String) : ContractInfoFormEvent()


    data class Submit(val mode: ScreenMode = ScreenMode.ADD): ContractInfoFormEvent()

}