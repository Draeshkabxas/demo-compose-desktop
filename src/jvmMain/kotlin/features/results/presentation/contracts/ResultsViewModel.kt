package features.results.presentation.results

import features.results.domain.usecases.PrintResultsListToXlsxFile
import utils.Resource
import features.results.domain.model.Results
import features.results.domain.usecases.GetAllResults
import features.results.domain.usecases.RemoveAllResults
import features.results.domain.usecases.RemoveResults
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
    private val removeResultsUseCase: RemoveResults
) {
    private var state  = FilterState()

    private val resultsDataChannel = Channel<List<Results>>()

    private var resultsData:List<Results> = emptyList()
    val resultsDataFlow = resultsDataChannel.receiveAsFlow()

    private var printList = listOf<String>()
    private var printPath = ""

    init {
        getFilterData()
    }
    fun onEvent(event: FilterEvent) {
        when (event) {
//            is FilterEvent.FilterLibyaId -> {
//                state = state.copy(libyaId = event.libyaId)
//            }
//
//            is FilterEvent.FilterFileNumber -> {
//                state = state.copy(fileNumber = event.fileNumber)
//            }
//
//            is FilterEvent.FilterEducationLevel -> {
//                state = state.copy(educationLevel = event.educationLevel)
//            }

            is FilterEvent.FilterDate -> {
                state = state.copy(date = event.date)
            }

            is FilterEvent.FilterName -> {
                state = state.copy(personName = event.personName)
            }

//            is FilterEvent.FilterMotherName -> {
//                state = state.copy(motherName = event.motherName)
//            }
//            is FilterEvent.FilterAgeGroup -> {
//                state = state.copy(ageGroup = event.ageGroup.fromArabicNameToAgeGroup())
//            }

            is FilterEvent.Reset -> {
                state = FilterState()
                getFilterData()
            }

            is FilterEvent.Submit -> {
                getFilterData()
            }
        }
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
        printResultsListToXlsxFile.invoke(resultsData, filePath,printList).onEach {
            when (it) {
                is Resource.Error -> onError(it.message.toString())
                is Resource.Loading -> onLoading()
                is Resource.Success -> it.data?.let(onSuccess)
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

