package features.sons_of_officers.domain.usecases

import common.Resource
import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class GetAllPeople(
    private val personRepository: PersonRepository
) {
    operator fun invoke(filters:Map<String,String>): Flow<Resource<List<Person>>> = flow{
        emit(Resource.Loading(data = emptyList()))
        var filterQuery=""
        filters.toList().forEachIndexed { index, (filterName,filterValue) ->
            if (filterValue.isEmpty()) {
                return@forEachIndexed
            }
            filterQuery += if (index == filters.size-1){
                "$filterName == $filterValue"
            }else{
                "$filterName == $filterValue && "
            }
        }

        val result=personRepository.getAllPeople(filterQuery)
        emit(Resource.Success(result.first()))
    }.catch { emit(Resource.Error("Cloud Not add new person")) }
}