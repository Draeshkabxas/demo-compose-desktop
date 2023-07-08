package features.courses.presentation.courses

import common.Resource
import features.courses.domain.model.Course
import features.courses.domain.usecases.GetAllCourses
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import utils.fromArabicNameToAgeGroup

class CoursesScreenViewModel(
    private val getAllCourses: GetAllCourses,
) {
    private var state  = FilterState()

    private val coursesDataChannel = Channel<List<Course>>()

    private var coursesData:List<Course> = emptyList()
    val coursesDataFlow = coursesDataChannel.receiveAsFlow()

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
                state = state.copy(healthStatus = event.healthStatus.toString())
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

    fun printToXlsxFile(
        filePath: String,
        onError: (String) -> Unit,
        onLoading: () -> Unit,
        onSuccess: (Boolean) -> Unit
    ) {

    }


    private fun getFilterData() {
        try {
            getAllCourses.invoke(state).onEach {
                when (it) {
                    is Resource.Error -> {
                        println(it.message)
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        it.data?.let { courses ->
                            coursesData = courses
                            coursesDataChannel.send(courses)
                        }
                    }
                }
            }.launchIn(CoroutineScope(Dispatchers.IO))
        } catch (e: Exception) {
            println(e.localizedMessage)
        }

    }


}