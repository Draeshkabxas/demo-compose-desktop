package features.contracts.presentation.add_contracts

import features.contracts.domain.model.Contract


data class ContractInfoFormState(
    val id:String ="",
    val name: String = "",
    val nameError: String? = null,
    val motherName: String = "",
    val motherNameError: String? = null,
    val motherNationality:String ="",
    val motherNationalityError:String? = null,
    val fileNumber: String = "",
    val fileNumberError: String? = null,
    val libyaId: String = "",
    val libyaIdError: String? = null,
    val phoneNumber: String = "",
    val phoneNumberError: String? = null,
    val dependency:String ="",
    val dependencyError:String?=null,
    val educationLevel: String = "",
    val educationLevelError: String? = null,
    val bankName: String = "",
    val bankNameError: String? = null,
    val accountNumber: String = "",
    val accountNumberError: String? = null,
    val city: String = "",
    val cityError: String? = null,
    val notes: String = "",
    val notesError: String? = null,
)
fun Contract.toContractInfoFormState(): ContractInfoFormState =
    ContractInfoFormState(
        id = this.id,
        name = this.name,
        motherName = this.motherName,
        motherNationality = this.motherNationality,
        fileNumber = this.fileNumber,
        libyaId = this.libyaId,
        phoneNumber = this.phoneNumber,
        educationLevel = this.educationLevel,
        bankName = this.bankName,
        accountNumber = this.accountNumber,
        dependency = this.dependency,
        city = this.city,
        notes =this.notes
    )