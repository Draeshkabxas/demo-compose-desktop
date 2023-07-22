package features.courses.presentation.courses

import utils.Resource
import features.courses.domain.model.Course
import features.courses.domain.usecases.GetAllCourses
import features.courses.domain.usecases.PrintCoursesListToXlsxFile
import features.courses.domain.usecases.RemoveAllCourses
import features.courses.domain.usecases.RemoveCourse
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import utils.fromArabicNameToAgeGroup

class CoursesScreenViewModel(
    private val getAllCourses: GetAllCourses,
    private val printCoursesListToXlsxFile: PrintCoursesListToXlsxFile,
    private val removeAllCourses: RemoveAllCourses,
    private val removeCourseUseCase:RemoveCourse
) {
    private var state  = FilterState()

    private val coursesDataChannel = Channel<List<Course>>()

    private var coursesData:List<Course> = emptyList()
    val coursesDataFlow = coursesDataChannel.receiveAsFlow()

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

        }
    }

    fun removeCourse(
        course: Course,
        onLoading: () -> Unit = {},
        onError: (String) -> Unit = {},
        onSuccess: (Boolean) -> Unit
    ) {
        removeCourseUseCase(course).onEach {
            when (it) {
                is Resource.Error -> onError(it.message.toString())
                is Resource.Loading -> onLoading()
                is Resource.Success ->{
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

    fun removeAllCourses(onLoading: () -> Unit, onError: (String) -> Unit, onSuccess: (Boolean) -> Unit){
        removeAllCourses.invoke().onEach {
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

    private fun printToXlsxFile(
        filePath: String,
        printList:List<String>,
        onError: (String) -> Unit,
        onLoading: () -> Unit,
        onSuccess: (Boolean) -> Unit
    ) {
        printCoursesListToXlsxFile.invoke(coursesData, filePath,printList).onEach {
            when (it) {
                is Resource.Error -> onError(it.message.toString())
                is Resource.Loading -> onLoading()
                is Resource.Success -> it.data?.let(onSuccess)
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
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