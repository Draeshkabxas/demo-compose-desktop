package features.sons_of_officers.presentation.sons_of_officers

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import utils.Resource
import features.courses.presentation.courses.FilterEvent
import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.domain.usecases.GetAllPeople
import features.sons_of_officers.domain.usecases.PrintPersonsListToXlsxFile
import features.sons_of_officers.domain.usecases.RemoveAllPeople
import features.sons_of_officers.domain.usecases.RemovePerson
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import utils.fromArabicNameToAgeGroup

class SonsOfOfficersScreenViewModel(
    private val getAllPeople: GetAllPeople,
    private val printPersonsListToXlsxFile: PrintPersonsListToXlsxFile,
    private val removeAllPeople: RemoveAllPeople,
    private val removePersonUseCase: RemovePerson
) {
    private var state = FilterState()

    private val peopleDataChannel = Channel<List<Person>>()

    private var peopleData: List<Person> = emptyList()
    val peopleDataFlow = peopleDataChannel.receiveAsFlow()

    private var printList = listOf<String>()
    private var printPath = ""
    val checkedPersons = mutableStateOf<MutableList<Person>>(mutableListOf())

    init {
        getFilterData()
    }

    fun onEvent(event: FilterEvent) {
        when (event) {
            is FilterEvent.FilterName -> {
                state = state.copy(personName = event.personName)
            }
            is FilterEvent.FilterLibyaId -> {
                state = state.copy(libyaId = event.libyaId)
            }

            is FilterEvent.FilterFileNumber -> {
                state = state.copy(fileNumber = event.fileNumber)
            }

            is FilterEvent.FilterEducationLevel -> {
                state = state.copy(educationLevel = event.educationLevel)
            }

            is FilterEvent.FilterCity -> {
                state = state.copy(city = event.city)
            }

            is FilterEvent.FilterFileState -> {
                state = state.copy(fileState = event.fileState.toString())
            }

            is FilterEvent.FilterReferralForTraining -> {
                state = state.copy(referralForTraining = event.referralForTraining.toString())
            }

            is FilterEvent.FilterAgeGroup -> {
                state = state.copy(ageGroup = event.ageGroup.fromArabicNameToAgeGroup())
            }

            is FilterEvent.FilterHealthStatus -> {
                state = state.copy(healthStatus = event.healthStatus)
            }

            is FilterEvent.Reset -> {
                state = FilterState()
                getFilterData()
            }

            is FilterEvent.Submit -> {
                getFilterData()
            }

            is FilterEvent.FilterName -> TODO()
        }
    }

    fun removePerson(
        person: Person,
        onLoading: () -> Unit = {},
        onError: (String) -> Unit = {},
        onSuccess: (Boolean) -> Unit
    ) {
        removePersonUseCase(person).onEach {
            when (it) {
                is Resource.Error -> onError(it.message.toString())
                is Resource.Loading -> onLoading()
                is Resource.Success -> {
                    onSuccess(it.data ?: true)
                    getFilterData()
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }


    fun removeAllPeople(onLoading: () -> Unit, onError: (String) -> Unit, onSuccess: (Boolean) -> Unit) {
        removeAllPeople.invoke().onEach {
            when (it) {
                is Resource.Error -> onError(it.message.toString())
                is Resource.Loading -> onLoading()
                is Resource.Success -> {
                    onSuccess(it.data ?: true)
                    getFilterData()
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    fun onPrintEvent(event: PrintEvent) {
        when (event) {
            is PrintEvent.PrintList -> printList = event.list
            is PrintEvent.PrintToDirectory -> printPath = event.path
            PrintEvent.Submit -> printToXlsxFile(printPath, printList, {}, {}, {})
        }

    }


    fun printToXlsxFile(
        filePath: String,
        printList: List<String>,
        onError: (String) -> Unit,
        onLoading: () -> Unit,
        onSuccess: (Boolean) -> Unit
    ) {
        val printData = if (checkedPersons.value.isEmpty()) peopleData else checkedPersons.value
        printPersonsListToXlsxFile.invoke(printData, filePath, printList).onEach {
            when (it) {
                is Resource.Error -> onError(it.message.toString())
                is Resource.Loading -> onLoading()
                is Resource.Success -> {
                    checkedPersons.value.clear()
                    it.data?.let(onSuccess)
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }


    private fun getFilterData() {
        try {
            getAllPeople.invoke(state).onEach {
                when (it) {
                    is Resource.Error -> {
                        println(it.message)
                    }

                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        it.data?.let { people ->
                            peopleData = people
                            peopleDataChannel.send(people)
                        }
                    }
                }
            }.launchIn(CoroutineScope(Dispatchers.IO))
        } catch (e: Exception) {
            println(e.localizedMessage)
        }

    }


}