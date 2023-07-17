package features.courses.domain.usecases

import utils.Resource
import features.courses.domain.model.Course
import features.courses.domain.repository.CoursesRepository
import features.courses.presentation.courses.FilterState
import kotlinx.coroutines.flow.*
import utils.HealthStatus
import utils.HealthStatus.All

class GetAllCourses(
    private val courseRepository: CoursesRepository
) {
    operator fun invoke(filters: FilterState): Flow<Resource<List<Course>>> = flow{
        emit(Resource.Loading(data = emptyList()))
        println(filters)

        val result=courseRepository.getAllCourses("")
        println("get all people before filters")
        var resultAfterFiltered = result.first().filter {
            println(it)
            it.libyaId.contains(filters.libyaId) &&
            it.fileNumber.contains(filters.fileNumber) &&
            it.educationLevel.contains(filters.educationLevel) &&
            it.city.contains(filters.city) &&
            if (filters.ageGroup != null) it.ageGroup == filters.ageGroup else true
        }

        if (filters.healthStatus != All){
            resultAfterFiltered = resultAfterFiltered.filter {
                when (filters.healthStatus) {
                    HealthStatus.APPROPRIATE -> {
                        println("لائق صحيا")
                        it.procedures["لائق صحيا"] == true
                    }
                    HealthStatus.INAPPROPRIATE -> {
                        println("غير لائق صحيا")
                        it.procedures["غير لائق صحيا"]== true
                    }
                    else -> {
                        true
                    }
                }
            }
        }

        if (filters.referralForTraining.isNotEmpty()) {
           resultAfterFiltered = resultAfterFiltered.filter {
                if (it.procedures["إحالة لتدريب"] == null) {
                    return@filter false
                }
                println(it.procedures["إحالة لتدريب"] )
                println(filters.referralForTraining.toBooleanStrict())
                it.procedures["إحالة لتدريب"] == filters.referralForTraining.toBooleanStrict()
            }
        }
        if (filters.fileState.isNotEmpty()) {
         resultAfterFiltered = resultAfterFiltered.filter {
                filters.fileState.toBooleanStrict() == it.justificationsRequire.all { requireValue-> requireValue.value }
            }
        }
        println("get all people after filters $resultAfterFiltered")
        emit(Resource.Success(resultAfterFiltered))
    }.catch { emit(Resource.Error("Cloud Not add new Course ${it.localizedMessage}")) }
}