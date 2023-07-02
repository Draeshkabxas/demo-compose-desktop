package features.sons_of_officers.domain.usecases

import common.Resource
import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class GetAllPeopleUseCase(
    private val personRepository: PersonRepository
) {
    operator fun invoke(): Flow<Resource<List<Person>>> = flow{
        emit(Resource.Loading(data = emptyList()))
        val result=personRepository.getAllPeople()
        println("adding new person state  "+result.first())
        emit(Resource.Success(result.first()))
    }.catch { emit(Resource.Error("Cloud Not add new person")) }
}