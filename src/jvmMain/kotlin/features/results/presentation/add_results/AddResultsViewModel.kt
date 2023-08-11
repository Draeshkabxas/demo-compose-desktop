package features.results.presentation.add_results

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import common.component.ScreenMode
import features.results.domain.model.Results
import features.results.domain.usecases.AddResults
import features.results.domain.usecases.UpdateResults
import features.sons_of_officers.domain.usecases.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import utils.getAgeGroupFromLibyaId


class AddResultsViewModel(
    private val validateLibyaId: ValidateLibyaId = ValidateLibyaId(),
    private val validatePhoneNumber: ValidatePhoneNumber = ValidatePhoneNumber(),
    private val validateTextInputs: ValidateTextInputs = ValidateTextInputs(),
    private val validateQuadrupleName: ValidateQuadrupleName = ValidateQuadrupleName(),
    private val addResults: AddResults,
    private val updateResults: UpdateResults

) {

    var state by mutableStateOf(ResultsInfoFormState())

//    private val addContractChannel = Channel<ValidationEvent>()
//    val addContractEvent = addContractChannel.receiveAsFlow()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun setupMode(mode: ScreenMode, results: Results?){
        if (mode == ScreenMode.EDIT && results !=null){
            state = results.toResultsInfoFormState()
            println("Setup mode state = $state")
//            justificationsRequiredInputsNameAndValue =
//                contract.justificationsRequire.map { it.key to mutableStateOf(it.value) }.toMap()
//            proceduresInputNameAndValues =
//                contract.procedures.map { it.key to mutableStateOf(it.value) }.toMap()
        }
    }


    fun onEvent(event: ResultsInfoFormEvent) {
        when (event) {
            is ResultsInfoFormEvent.NameChanged -> {
                state = state.copy(name = event.name)
            }
//
//            is ResultsInfoFormEvent.MotherNameChanged -> {
//                state = state.copy(motherName = event.name)
//            }
//
//            is ResultsInfoFormEvent.MotherNationalityChanged -> {
//                state = state.copy(motherNationality = event.name)
//            }
//
//            is ResultsInfoFormEvent.FileNumberChanged -> {
//                state = state.copy(fileNumber = event.fileNumber)
//            }
//
//            is ResultsInfoFormEvent.LibyaIdChanged -> {
//                state = state.copy(libyaId = event.libyaId)
//            }

            is ResultsInfoFormEvent.PhoneNumberChanged -> {
                state = state.copy(phoneNumber = event.phone)
            }

            is ResultsInfoFormEvent.ResultChanged -> {
                state = state.copy(result = event.result)
            }

//            is ResultsInfoFormEvent.BankNameChanged -> {
//                state = state.copy(bankName = event.bankName)
//            }
//
//            is ResultsInfoFormEvent.AccountNumberChanged -> {
//                state = state.copy(accountNumber = event.accountNumber)
//            }
//
//            is ResultsInfoFormEvent.EducationLevelChanged -> {
//                state = state.copy(educationLevel = event.educationLevel)
//            }

            is ResultsInfoFormEvent.DateChanged -> {
                state = state.copy(date = event.date)
            }

            is ResultsInfoFormEvent.NotesChanged -> {
                state = state.copy(notes = event.notes)
            }

            is ResultsInfoFormEvent.Submit -> {
                submitData(event.mode)
            }
        }
    }


    private fun submitData(mode: ScreenMode) {
        val nameResult = validateQuadrupleName.execute(state.name)
//        val motherResult = validateTextInputs.execute(state.motherName, "اسم الأم")
//        val motherNationalityResult = validateTextInputs.execute(state.motherNationality, "جنسية الام")
//        val fileNumberResult = validateTextInputs.execute(state.fileNumber, "رقم الملف", true)
//        val libyaIdResult = validateLibyaId.execute(state.libyaId)
        val phoneNumberResult = validatePhoneNumber.execute(state.phoneNumber)
        val resultResult = validateTextInputs.execute(state.result, "نتائج التحاليل")
//        val bankNameResult = validateTextInputs.execute(state.bankName, "إسم المصرف")
//        val accountNumberResult = validateTextInputs.execute(state.accountNumber, "رقم الحساب",true)
//        val educationLevelResult = validateTextInputs.execute(state.educationLevel, "المؤهل العلمي")
        val dateResult = validateTextInputs.execute(state.date, "تاريخ التحاليل ",true)

        val hasError = listOf(
            nameResult,
//            motherResult,
//            motherNationalityResult,
//            fileNumberResult,
//            libyaIdResult,
            phoneNumberResult,
            resultResult,
//            bankNameResult,
//            accountNumberResult,
//            educationLevelResult,
            dateResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                nameError = nameResult.errorMessage,
//                motherNameError = motherResult.errorMessage,
//                motherNationalityError = motherNationalityResult.errorMessage,
//                fileNumberError = fileNumberResult.errorMessage,
//                libyaIdError = libyaIdResult.errorMessage,
                phoneNumberError = phoneNumberResult.errorMessage,
                resultError = resultResult.errorMessage,
//                bankNameError = bankNameResult.errorMessage,
//                accountNumberError = accountNumberResult.errorMessage,
//                educationLevelError = educationLevelResult.errorMessage,
                dateError = dateResult.errorMessage
            )
            return
        }
        val newResults = Results(
            id = state.id,
            name = state.name,
//            motherName = state.motherName,
//            motherNationality = state.motherNationality,
//            fileNumber = state.fileNumber,
//            libyaId = state.libyaId,
            phoneNumber = state.phoneNumber,
            result = state.result,
//            bankName = state.bankName,
//            accountNumber = state.accountNumber,
//            educationLevel = state.educationLevel,
            date = state.date,
//            ageGroup = getAgeGroupFromLibyaId(state.libyaId),
            notes = state.notes,
        )

        if (mode == ScreenMode.ADD) {
            addResults.invoke(newResults).onEach {
                if (it.data == true){
                    validationEventChannel.send(ValidationEvent.Success)
                    println("submitData add is getting data")
                    state = ResultsInfoFormState()
                    validationEventChannel.send(ValidationEvent.New)
                }
            }.launchIn(CoroutineScope(Dispatchers.IO))
        }else{
            updateResults.invoke(newResults).onEach {
                if (it.data == true){
                    validationEventChannel.send(ValidationEvent.Success)
                    println("submitData update is getting data")
                    state = ResultsInfoFormState()
                    validationEventChannel.send(ValidationEvent.New)
                }
            }.launchIn(CoroutineScope(Dispatchers.IO))
        }
//        addContract.invoke(newContract).onEach {
//            addContractChannel.send(ValidationEvent.Success)
//            state = AddContractFormState()
//            addContractChannel.send(ValidationEvent.New)
//        }.launchIn(CoroutineScope(Dispatchers.IO))



    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
        object New : ValidationEvent()
    }
}
