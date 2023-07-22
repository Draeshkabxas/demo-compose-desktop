package features.contracts.presentation.contracts

import utils.Resource
import features.contracts.domain.model.Contract
import features.contracts.domain.usecases.GetAllContracts;
import features.contracts.domain.usecases.PrintContractsListToXlsxFile
import features.contracts.domain.usecases.RemoveAllContracts
import features.contracts.domain.usecases.RemoveContract
import features.courses.domain.model.Course
import features.sons_of_officers.presentation.sons_of_officers.PrintEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import utils.fromArabicNameToAgeGroup

class ContractsScreenViewModel (
    private val allContracts:GetAllContracts,
    private val printContractsListToXlsxFile: PrintContractsListToXlsxFile,
    private val removeAllContracts: RemoveAllContracts,
    private val removeContractUseCase: RemoveContract
) {
    private var state  = FilterState()

    private val contractsDataChannel = Channel<List<Contract>>()

    private var contractsData:List<Contract> = emptyList()
    val contractsDataFlow = contractsDataChannel.receiveAsFlow()

    private var printList = listOf<String>()
    private var printPath = ""

    init {
        getFilterData()
    }
    fun onEvent(event: FilterEvent) {
        when (event) {
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

            is FilterEvent.FilterName -> {
                state = state.copy(personName = event.personName)
            }

            is FilterEvent.FilterMotherName -> {
                state = state.copy(motherName = event.motherName)
            }
            is FilterEvent.FilterAgeGroup -> {
                state = state.copy(ageGroup = event.ageGroup.fromArabicNameToAgeGroup())
            }

            is FilterEvent.Reset -> {
                state = FilterState()
                getFilterData()
            }

            is FilterEvent.Submit -> {
                getFilterData()
            }
        }
    }

    fun removeContract(
       contract: Contract,
        onLoading: () -> Unit = {},
        onError: (String) -> Unit = {},
        onSuccess: (Boolean) -> Unit
    ) {
        removeContractUseCase(contract).onEach {
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


    fun removeAllContracts(onLoading: () -> Unit, onError: (String) -> Unit, onSuccess: (Boolean) -> Unit){
        removeAllContracts.invoke().onEach {
            when(it){
                is Resource.Error -> onError(it.message.toString())
                is Resource.Loading -> onLoading()
                is Resource.Success -> {
                    onSuccess(it.data ?: true)
                    getFilterData()
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    fun onPrintEvent(event: PrintEvent){
        when(event){
            is PrintEvent.PrintList -> printList = event.list
            is PrintEvent.PrintToDirectory -> printPath = event.path
            PrintEvent.Submit -> printToXlsxFile(printPath,printList,{},{},{})
        }
    }

    private fun printToXlsxFile(
        filePath: String,
        printList:List<String>,
        onError: (String) -> Unit,
        onLoading: () -> Unit,
        onSuccess: (Boolean) -> Unit
    ) {
        printContractsListToXlsxFile.invoke(contractsData, filePath,printList).onEach {
            when (it) {
                is Resource.Error -> onError(it.message.toString())
                is Resource.Loading -> onLoading()
                is Resource.Success -> it.data?.let(onSuccess)
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    private fun getFilterData() {
        try {
            allContracts.invoke(state).onEach {
                when (it) {
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        it.data?.let { people ->
                            contractsData = people
                            contractsDataChannel.send(people)
                        }
                    }
                }
            }.launchIn(CoroutineScope(Dispatchers.IO))
        } catch (e: Exception) {
            println(e.localizedMessage)
        }

    }


}

