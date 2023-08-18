package features.contracts.presentation.contracts

import androidx.compose.runtime.*
import utils.Resource
import features.contracts.domain.model.Contract
import features.contracts.domain.usecases.*
import features.sons_of_officers.presentation.sons_of_officers.PrintEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import utils.Education
import utils.fromArabicNameToAgeGroup
import utils.getAllEducationArabicNames

class ContractsScreenViewModel (
    private val allContracts:GetAllContracts,
    private val printContractsListToXlsxFile: PrintContractsListToXlsxFile,
    private val removeAllContracts: RemoveAllContracts,
    private val importContractsFromXlsx: ImportContractsFromXlsx,
    private val changeContractsEducationLevel: ChangeAllContractsEducationLevel,
    private val changeAllContractsCity: ChangeAllContractsCity,
    private val addAllContract: AddAllContract,
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

    @Composable
    fun currentPageReset() {
        var currentPage by remember { mutableStateOf(0) }
    }
    fun importContracts(
        filePath: String,
        onLoading: () -> Unit = {},
        onError: (String) -> Unit = {},
        onSuccess: (Map<String,List<Contract>>) -> Unit
    ) {
        importContractsFromXlsx(filePath).onEach {
            when (it) {
                is Resource.Error -> onError(it.message.toString())
                is Resource.Loading -> onLoading()
                is Resource.Success -> {
                    val contracts = getContractsGroupedByEducationLevel(it.data ?: emptyList())
                    onSuccess(contracts)
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    fun checkIfEducationLevelIsCorrect(educationLevel: String): Boolean {
        return getAllEducationArabicNames().contains(educationLevel)
    }

    fun changeContractCityType(
        contracts:Map<String,List<Contract>>,
        convertedMap: Map<String,String>,
        onError: (String) -> Unit = {},
        onSuccess: (List<Contract>?) -> Unit
    ) {
        if (convertedMap.values.any { it.isEmpty() }){
            println("converted map = $convertedMap")
            onError("من فضلك حدد المدينة لجميع الفئاة")
            onSuccess(null)
        }else{
            val contracts = changeAllContractsCity(contracts,convertedMap)
            onSuccess(contracts)
        }
    }
    fun changeContractEducationLevelType(
        contracts:Map<String,List<Contract>>,
        convertedMap: Map<String,String>,
        onError: (String) -> Unit = {},
        onSuccess: (List<Contract>?) -> Unit
    ) {
        if (convertedMap.values.any { it.isEmpty() }){
            println("converted map = $convertedMap")
            onError("من فضلك حدد المؤهل العلمي لجميع الفئاة")
            onSuccess(null)
        }else{
            val contracts = changeContractsEducationLevel(contracts,convertedMap)
            onSuccess(contracts)
        }
    }

    fun addAllImportedContracts(
        contracts: List<Contract>,
        onLoading: () -> Unit = {},
        onError: (String) -> Unit = {},
        onSuccess: (Boolean) -> Unit
    ){
        addAllContract(contracts).onEach {
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


    private fun getContractsGroupedByEducationLevel(contracts: List<Contract>): Map<String, List<Contract>> {
        return contracts.groupBy { it.educationLevel }
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

