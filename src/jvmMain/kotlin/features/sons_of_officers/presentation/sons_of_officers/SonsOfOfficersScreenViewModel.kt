package features.sons_of_officers.presentation.sons_of_officers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import common.Resource
import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.domain.usecases.GetAllPeople
import features.sons_of_officers.domain.usecases.PrintPersonsListToXlsxFile
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SonsOfOfficersScreenViewModel(
private val getAllPeople: GetAllPeople,
private val printPersonsListToXlsxFile: PrintPersonsListToXlsxFile
) {
    var state by mutableStateOf(FilterState())
     var peopleData by mutableStateOf<List<Person>>(emptyList())

    init {
        CoroutineScope(Dispatchers.Default).launch{
            delay(timeMillis = 1000)
            getFilterData()
        }
    }



    fun onEvent(event: FilterEvent){
        when(event){
            is FilterEvent.FilterLibyaId -> {
                state = state.copy(libyaId = event.libyaId)
            }
            is FilterEvent.FilterFileNumber -> {
                state = state.copy(fileNumber = event.fileNumber)
            }
            is FilterEvent.FilterEducationLevel ->{
                state = state.copy(educationLevel = event.educationLevel)
            }
            is FilterEvent.FilterCity -> {
                state = state.copy(city = event.city)
            }
            is FilterEvent.FilterFileState ->{
                state = state.copy(fileState = event.fileState.toString())
            }
            is FilterEvent.FilterReferralForTraining -> {
                state = state.copy(referralForTraining = event.referralForTraining.toString())
            }
            is FilterEvent.Reset ->{
              state = FilterState()
                getFilterData()
            }
            is FilterEvent.Submit -> {
                getFilterData()
            }
            else -> {}
        }
    }

     fun printToXlsxFile(filePath:String,onError:(String)->Unit,onLoading:() -> Unit,onSuccess:(Boolean)->Unit){
        printPersonsListToXlsxFile.invoke(peopleData,filePath).onEach {
            when(it){
                is Resource.Error -> onError(it.message.toString())
                is Resource.Loading -> onLoading()
                is Resource.Success -> it.data?.let(onSuccess)
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }


    private fun getFilterData() {
        getAllPeople.invoke(state).onEach {
            when(it){
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    it.data?.let {people->
                            peopleData = people
                    }
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))

    }


}