package features.contracts.presentation.contracts

import common.Resource
import features.contracts.domain.model.Contract
import features.contracts.domain.usecases.GetAllContracts;
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import utils.fromArabicNameToAgeGroup
import utils.getAgeGroupFromLibyaId

class ContractsScreenViewModel (
    private val allContracts:GetAllContracts
) {
    private var state  = FilterState()

    private val contractsDataChannel = Channel<List<Contract>>()

    private var contractsData:List<Contract> = emptyList()
    val contractsDataFlow = contractsDataChannel.receiveAsFlow()

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

