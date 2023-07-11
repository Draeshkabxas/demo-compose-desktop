package features.sons_of_officers.presentation.add_sons_of_officers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import common.component.ScreenMode
import common.component.ScreenMode.ADD
import common.component.ScreenMode.EDIT
import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.domain.usecases.*
import features.sons_of_officers.presentation.add_sons_of_officers.PersonalInfoFormEvent.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import utils.getAgeGroupFromLibyaId

class AddSonsOfOfficersViewModel(
    private val validateLibyaId: ValidateLibyaId =ValidateLibyaId(),
    private val validatePhoneNumber: ValidatePhoneNumber = ValidatePhoneNumber(),
    private val validateTextInputs: ValidateTextInputs = ValidateTextInputs(),
    private val validateQuadrupleName: ValidateQuadrupleName = ValidateQuadrupleName(),
    private val addPerson: AddPerson,
    private val updatePerson: UpdatePerson
) {

    var state by mutableStateOf(PersonalInfoFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun setupMode(mode: ScreenMode,person: Person?){
        if (mode == EDIT && person !=null){
            state = person.toPersonalInfoFormState()
            justificationsRequiredInputsNameAndValue =
                person.justificationsRequire.map { it.key to mutableStateOf(it.value) }.toMap()
            proceduresInputNameAndValues =
                person.procedures.map { it.key to mutableStateOf(it.value) }.toMap()
        }
    }

    val personalInputsNameAndValue = listOf(
        "الاسم رباعي",
        "اسم الام",
        "رقم الملف",
        "الرقم الوطني",
        "رقم الهاتف",
        "المؤهل العلمي",
        "القائم بالتجنيد",
        "المدينة",
    )

    var justificationsRequiredInputsNameAndValue = mapOf(
        "ملف" to mutableStateOf(false),
        "السيرة الذاتية" to mutableStateOf(false),
        "عدد 8 صور " to mutableStateOf(false),
        "شهادة الخلو من السوابق الجنائية" to mutableStateOf(false),
        "شهادة الوضع العائلي" to mutableStateOf(false),
        "افادة بعدم الارتباط بعمل" to mutableStateOf(false),
        "شهادة بعدم الجواز بأجنبية" to mutableStateOf(false),
        "شهادة بالاقامة من المجلس المحلي" to mutableStateOf(false),
        "تصوير كتيب العائلة بالكامل" to mutableStateOf(false),
        "المؤهل العلمي (افادة + كشف درجات الاصلي ) معتمد" to mutableStateOf(false),
        "طلب كتابي" to mutableStateOf(false),
        "موافقة ولي الامر" to mutableStateOf(false),
        "شهادة الجنسية" to mutableStateOf(false),
        " شهادة الدرن" to mutableStateOf(false),
        "الرقم الوطني" to mutableStateOf(false)
    )
    var proceduresInputNameAndValues = mapOf(
        "تحاليل" to mutableStateOf(false),
        "كشف طبي" to mutableStateOf(false),
        "لائق صحيا" to mutableStateOf(false),
        "غير لائق صحيا" to mutableStateOf(false),
        "مقابلة شخصية" to mutableStateOf(false),
        "إحالة لتدريب" to mutableStateOf(false),
    )


    fun onEvent(event: PersonalInfoFormEvent){
        when(event){
            is NameChanged -> {
                state = state.copy(name = event.name)
            }
            is MotherNameChanged -> {
                state = state.copy(motherName =  event.name)
            }
            is FileNumberChanged ->{
                state = state.copy(fileNumber =  event.fileNumber)
            }
            is LibyaIdChanged -> {
                println("Libya id change = ${event.libyaId}")
                state = state.copy(libyaId = event.libyaId)
            }
            is PhoneNumberChanged -> {
                state = state.copy(phoneNumber = event.phone)
            }
            is EducationLevelChanged ->{
                state = state.copy(educationLevel = event.educationLevel)
            }
            is RecruiterChanged -> {
                state = state.copy(recruiter = event.recruiter)
            }
            is CityChanged ->{
                state = state.copy(city = event.city)
            }
            is Submit -> {
                submitData(event.mode)
            }
        }
    }


    private fun submitData(mode: ScreenMode) {
        val nameResult = validateQuadrupleName.execute(state.name)
        val motherResult = validateTextInputs.execute(state.motherName,"اسم الأم")
        val fileNumberResult = validateTextInputs.execute(state.fileNumber,"رقم الملف",true)
        val libyaIdResult = validateLibyaId.execute(state.libyaId)
        val phoneNumberResult = validatePhoneNumber.execute(state.phoneNumber)
        val educationLevelResult= validateTextInputs.execute(state.educationLevel,"المؤهل العلمي")
        val recruiterResult = validateTextInputs.execute(state.recruiter,"القائم بالتجنيد")
        val cityResult = validateTextInputs.execute(state.city,"المدينة ")

        val hasError = listOf(
            nameResult,
            motherResult,
            fileNumberResult,
            libyaIdResult,
            phoneNumberResult,
            educationLevelResult,
            recruiterResult,
            cityResult
        ).any { !it.successful }

        if(hasError) {
            state = state.copy(
                nameError = nameResult.errorMessage,
                motherNameError = motherResult.errorMessage,
                fileNumberError = fileNumberResult.errorMessage,
                libyaIdError = libyaIdResult.errorMessage,
                phoneNumberError = phoneNumberResult.errorMessage,
                educationLevelError = educationLevelResult.errorMessage,
                recruiterError = recruiterResult.errorMessage,
                cityError = cityResult.errorMessage
            )
            return
        }
        val justification = justificationsRequiredInputsNameAndValue.map {name ->
            name.key to name.value.value
        }.toMap()

        val procedures = proceduresInputNameAndValues.map {name ->
            name.key to name.value.value
        }.toMap()
        println(state)
        val newPerson= Person(
            id = state.id,
            name = state.name,
            motherName = state.motherName,
            fileNumber = state.fileNumber,
            libyaId = state.libyaId,
            phoneNumber = state.phoneNumber,
            educationLevel = state.educationLevel,
            recruiter = state.recruiter,
            city = state.city,
            ageGroup = getAgeGroupFromLibyaId(state.libyaId),
            justificationsRequire = justification,
            procedures = procedures
        )
        println("submitData is running")
        if (mode == ADD) {
            addPerson.invoke(newPerson).onEach {
                if (it.data == true){
                    validationEventChannel.send(ValidationEvent.Success)
                    println("submitData add is getting data")
                    state = PersonalInfoFormState()
                    validationEventChannel.send(ValidationEvent.New)
                }
            }.launchIn(CoroutineScope(Dispatchers.IO))
        }else{
            updatePerson.invoke(newPerson).onEach {
                if (it.data == true){
                    validationEventChannel.send(ValidationEvent.Success)
                    println("submitData update is getting data")
                    state = PersonalInfoFormState()
                    validationEventChannel.send(ValidationEvent.New)
                }
            }.launchIn(CoroutineScope(Dispatchers.IO))
        }
    }

    sealed class ValidationEvent {
        object Success: ValidationEvent()
        object New: ValidationEvent()
    }
}
