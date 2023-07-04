package features.sons_of_officers.presentation.sons_of_officers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.domain.usecases.GetAllPeople
import features.sons_of_officers.presentation.add_sons_of_officers.PersonalInfoFormEvent
import features.sons_of_officers.presentation.add_sons_of_officers.PersonalInfoFormState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SonsOfOfficersScreenViewModel(
private val getAllPeople: GetAllPeople
) {
    var state by mutableStateOf(FilterState())
    var peopleData by mutableStateOf<List<Person>>(emptyList())


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
            }
            FilterEvent.Submit -> {
                filterData()
            }
        }
    }

    private fun filterData() {
        getAllPeople.invoke(state.getFilterStateVariablesNamesAndValues()).onEach {
            it.data?.let {people->
                peopleData = people
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))

    }


}