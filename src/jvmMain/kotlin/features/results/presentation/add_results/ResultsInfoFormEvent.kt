package features.results.presentation.add_results


import common.component.ScreenMode

sealed class ResultsInfoFormEvent {
    data class NameChanged(val name: String) : ResultsInfoFormEvent()
//    data class MotherNameChanged(val name: String) : ResultsInfoFormEvent()
//    data class MotherNationalityChanged(val name: String) : ResultsInfoFormEvent()
//    data class FileNumberChanged(
//        val fileNumber: String
//    ) : ResultsInfoFormEvent()
//
//    data class LibyaIdChanged(val libyaId: String) : ResultsInfoFormEvent()
    data class PhoneNumberChanged(val phone: String) : ResultsInfoFormEvent()
//    data class EducationLevelChanged(val educationLevel: String) : ResultsInfoFormEvent()
//    data class BankNameChanged(val bankName: String) : ResultsInfoFormEvent()
//    data class AccountNumberChanged(val accountNumber: String) : ResultsInfoFormEvent()

    data class ResultChanged(val result: String) : ResultsInfoFormEvent()
    data class DateChanged(val date: String) : ResultsInfoFormEvent()
    data class NotesChanged(val notes: String) : ResultsInfoFormEvent()


    data class Submit(val mode: ScreenMode = ScreenMode.ADD): ResultsInfoFormEvent()

}