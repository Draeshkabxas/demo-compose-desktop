package features.contracts.presentation.add_contracts
sealed class AddContractFormEvent {
    data class NameChanged(val name: String) : AddContractFormEvent()
    data class MotherNameChanged(val name: String) : AddContractFormEvent()
    data class MotherNationalityChanged(val name: String) : AddContractFormEvent()
    data class FileNumberChanged(
        val fileNumber: String
    ) : AddContractFormEvent()

    data class LibyaIdChanged(val libyaId: String) : AddContractFormEvent()
    data class PhoneNumberChanged(val phone: String) : AddContractFormEvent()
    data class DependencyChanged(val dependency: String) : AddContractFormEvent()
    data class BankNameChanged(val bankName: String) : AddContractFormEvent()
    data class AccountNumberChanged(val accountNumber: String) : AddContractFormEvent()
    data class EducationLevelChanged(val educationLevel: String) : AddContractFormEvent()

    data class CityChanged(val city: String) : AddContractFormEvent()

    data class NotesChanged(val notes: String) : AddContractFormEvent()
    object Submit: AddContractFormEvent()
}
