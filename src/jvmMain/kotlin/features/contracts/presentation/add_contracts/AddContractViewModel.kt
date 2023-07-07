package features.contracts.presentation.add_contracts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import features.contracts.domain.model.Contract
import features.contracts.domain.usecases.AddContract
import features.sons_of_officers.domain.usecases.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import utils.getAgeGroupFromLibyaId


class AddContractViewModel(
    private val validateLibyaId: ValidateLibyaId = ValidateLibyaId(),
    private val validatePhoneNumber: ValidatePhoneNumber = ValidatePhoneNumber(),
    private val validateTextInputs: ValidateTextInputs = ValidateTextInputs(),
    private val validateQuadrupleName: ValidateQuadrupleName = ValidateQuadrupleName(),
    private val addContract: AddContract,
) {

    var state by mutableStateOf(AddContractFormState())

    private val addContractChannel = Channel<ValidationEvent>()
    val addContractEvent = addContractChannel.receiveAsFlow()
    fun onEvent(event: AddContractFormEvent) {
        when (event) {
            is AddContractFormEvent.NameChanged -> {
                state = state.copy(name = event.name)
            }

            is AddContractFormEvent.MotherNameChanged -> {
                state = state.copy(motherName = event.name)
            }

            is AddContractFormEvent.MotherNationalityChanged -> {
                state = state.copy(motherNationality = event.name)
            }

            is AddContractFormEvent.FileNumberChanged -> {
                state = state.copy(fileNumber = event.fileNumber)
            }

            is AddContractFormEvent.LibyaIdChanged -> {
                state = state.copy(libyaId = event.libyaId)
            }

            is AddContractFormEvent.PhoneNumberChanged -> {
                state = state.copy(phoneNumber = event.phone)
            }

            is AddContractFormEvent.DependencyChanged -> {
                state = state.copy(dependency = event.dependency)
            }

            is AddContractFormEvent.BankNameChanged -> {
                state = state.copy(bankName = event.bankName)
            }

            is AddContractFormEvent.AccountNumberChanged -> {
                state = state.copy(accountNumber = event.accountNumber)
            }

            is AddContractFormEvent.EducationLevelChanged -> {
                state = state.copy(educationLevel = event.educationLevel)
            }

            is AddContractFormEvent.CityChanged -> {
                state = state.copy(city = event.city)
            }

            is AddContractFormEvent.NotesChanged -> {
                state = state.copy(notes = event.notes)
            }

            AddContractFormEvent.Submit -> {
                submitData()
            }
        }
    }


    private fun submitData() {
        val nameResult = validateQuadrupleName.execute(state.name)
        val motherResult = validateTextInputs.execute(state.motherName, "اسم الأم")
        val motherNationalityResult = validateTextInputs.execute(state.motherNationality, "جنسية الام")
        val fileNumberResult = validateTextInputs.execute(state.fileNumber, "رقم الملف", true)
        val libyaIdResult = validateLibyaId.execute(state.libyaId)
        val phoneNumberResult = validatePhoneNumber.execute(state.phoneNumber)
        val dependencyResult = validateTextInputs.execute(state.dependency, "التبعية")
        val bankNameResult = validateTextInputs.execute(state.bankName, "إسم المصرف")
        val accountNumberResult = validateTextInputs.execute(state.accountNumber, "رقم الحساب",true)
        val educationLevelResult = validateTextInputs.execute(state.educationLevel, "المؤهل العلمي")
        val cityResult = validateTextInputs.execute(state.city, "المدينة ")

        val hasError = listOf(
            nameResult,
            motherResult,
            motherNationalityResult,
            fileNumberResult,
            libyaIdResult,
            phoneNumberResult,
            dependencyResult,
            bankNameResult,
            accountNumberResult,
            educationLevelResult,
            cityResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                nameError = nameResult.errorMessage,
                motherNameError = motherResult.errorMessage,
                motherNationalityError = motherNationalityResult.errorMessage,
                fileNumberError = fileNumberResult.errorMessage,
                libyaIdError = libyaIdResult.errorMessage,
                phoneNumberError = phoneNumberResult.errorMessage,
                dependencyError = dependencyResult.errorMessage,
                bankNameError = bankNameResult.errorMessage,
                accountNumberError = accountNumberResult.errorMessage,
                educationLevelError = educationLevelResult.errorMessage,
                cityError = cityResult.errorMessage
            )
            return
        }
        val newContract = Contract(
            id = "",
            name = state.name,
            motherName = state.motherName,
            motherNationality = state.motherNationality,
            fileNumber = state.fileNumber,
            libyaId = state.libyaId,
            phoneNumber = state.phoneNumber,
            dependency = state.dependency,
            bankName = state.bankName,
            accountNumber = state.accountNumber,
            educationLevel = state.educationLevel,
            city = state.city,
            ageGroup = getAgeGroupFromLibyaId(state.libyaId),
            notes = state.notes,
        )
        addContract.invoke(newContract).onEach {
            addContractChannel.send(ValidationEvent.Success)
            state = AddContractFormState()
            addContractChannel.send(ValidationEvent.New)
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
        object New : ValidationEvent()
    }
}
