package features.sons_of_officers.domain.usecases

import utils.Resource
import features.contracts.domain.repository.ContractRepository
import features.courses.domain.repository.CoursesRepository
import features.sons_of_officers.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class RemoveAllPeople(
    private val personRepository: PersonRepository
){
    operator fun invoke(): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        val result=personRepository.removeAll().first()
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("Cloud Not remove all courses")) }
}