package features.sons_of_officers.domain.usecases

import utils.Resource
import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.domain.repository.PersonRepository
import features.sons_of_officers.presentation.sons_of_officers.FilterState
import kotlinx.coroutines.flow.*
import utils.HealthStatus.*

class GetAllPeople(
    private val personRepository: PersonRepository
) {
    operator fun invoke(filters: FilterState): Flow<Resource<List<Person>>> = flow{
        emit(Resource.Loading(data = emptyList()))
        println("Filters in getAllPeople $filters")

        val result=personRepository.getAllPeople("")
        var resultAfterFiltered = result.first().filter {
            println(it)
            it.libyaId.contains(filters.libyaId) &&
            it.fileNumber.contains(filters.fileNumber) &&
                    it.name.contains(filters.personName)&&

                    it.educationLevel.contains(filters.educationLevel) &&
            it.city.contains(filters.city) &&
            if (filters.ageGroup != null) it.ageGroup == filters.ageGroup else true
        }

        if (filters.healthStatus != All){
            resultAfterFiltered = resultAfterFiltered.filter {
                when (filters.healthStatus) {
                    APPROPRIATE -> {
                        println("لائق صحيا")
                        it.procedures["لائق صحيا"] == true
                    }
                    INAPPROPRIATE -> {
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
        emit(Resource.Success(resultAfterFiltered))
    }.catch { emit(Resource.Error("Cloud Not add new person ${it.localizedMessage}")) }
}