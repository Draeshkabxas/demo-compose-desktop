package features.results.presentation.results

import androidx.compose.runtime.mutableStateOf
import features.contracts.domain.model.Contract
import utils.Resource
import features.results.domain.model.Results
import features.results.domain.usecases.*
import features.sons_of_officers.domain.model.Person
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

class ResultsScreenViewModel (
    private val allResults: GetAllResults,
    private val printResultsListToXlsxFile: PrintResultsListToXlsxFile,
    private val removeAllResults: RemoveAllResults,
    private val importResultsFromXlsx: ImportResultsFromXlsx,
    private val addAllResults: AddAllResults,
    private val removeResultsUseCase: RemoveResults
) {
    private var state  = FilterState()

    private val resultsDataChannel = Channel<List<Results>>()

    private var resultsData:List<Results> = emptyList()
    val resultsDataFlow = resultsDataChannel.receiveAsFlow()

    private var printList = listOf<String>()
    private var printPath = ""
    val checkedPersons = mutableStateOf<MutableList<Results>>(mutableListOf())

    init {
        getFilterData()
    }
    fun onEvent(event: FilterEvent) {
        when (event) {
            is FilterEvent.FilterDate -> {
                state = state.copy(date = event.date)
            }
            is FilterEvent.FilterName -> {
                state = state.copy(personName = event.personName)
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

    fun importResults(
        filePath: String,
        onLoading: () -> Unit = {},
        onError: (String) -> Unit = {},
        onSuccess: (List<Results>) -> Unit
    ) {
        importResultsFromXlsx(filePath).onEach {
            when (it) {
                is Resource.Error -> onError(it.message.toString())
                is Resource.Loading -> onLoading()
                is Resource.Success -> {
                    onSuccess(it.data ?: emptyList<Results>())
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    fun addAllImportedResults(
        results: List<Results>,
        onLoading: () -> Unit = {},
        onError: (String) -> Unit = {},
        onSuccess: (Boolean) -> Unit
    ){
        addAllResults(results).onEach {
            when (it) {
                is Resource.Error -> onError(it.message.toString())
                is Resource.Loading -> onLoading()
                is Resource.Success -> {
                    onSuccess(it.data ?: false)
                    getFilterData()
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }


    fun removeResults(
        results: Results,
        onLoading: () -> Unit = {},
        onError: (String) -> Unit = {},
        onSuccess: (Boolean) -> Unit
    ) {
        removeResultsUseCase(results).onEach {
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


    fun removeAllResults(onLoading: () -> Unit, onError: (String) -> Unit, onSuccess: (Boolean) -> Unit){
        removeAllResults.invoke().onEach {
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
        var printData = if (checkedPersons.value.isEmpty()) resultsData else checkedPersons.value

        printResultsListToXlsxFile.invoke(printData, filePath,printList).onEach {
            when (it) {
                is Resource.Error -> onError(it.message.toString())
                is Resource.Loading -> onLoading()
                is Resource.Success -> {
//                    printData=peopleData
                    it.data?.let(onSuccess)
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    private fun getFilterData() {
        try {
            allResults.invoke(state).onEach {
                when (it) {
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        it.data?.let { people ->
                            resultsData = people
                            resultsDataChannel.send(people)
                        }
                    }
                }
            }.launchIn(CoroutineScope(Dispatchers.IO))
        } catch (e: Exception) {
            println(e.localizedMessage)
        }

    }


}

