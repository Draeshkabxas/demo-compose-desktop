package features.sons_of_officers.domain.usecases

import common.Resource
import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.domain.repository.PersonRepository
import features.sons_of_officers.presentation.sons_of_officers.FilterState
import kotlinx.coroutines.flow.*

class GetAllPeople(
    private val personRepository: PersonRepository
) {
    operator fun invoke(filters: FilterState): Flow<Resource<List<Person>>> = flow{
        emit(Resource.Loading(data = emptyList()))
        println(filters)

        val result=personRepository.getAllPeople("")
        println("get all people before filters")
        var resultAfterFiltered = result.first().filter {
            println(it)
            it.libyaId.contains(filters.libyaId) &&
            it.fileNumber.contains(filters.fileNumber) &&
            it.educationLevel.contains(filters.educationLevel) &&
            it.city.contains(filters.city) &&
            if (filters.ageGroup != null) it.ageGroup == filters.ageGroup else true
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
    }.catch { emit(Resource.Error("Cloud Not add new person ${it.localizedMessage}")) }
}