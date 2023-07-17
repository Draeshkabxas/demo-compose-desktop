package features.sons_of_officers.domain.usecases

import utils.Resource
import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class AddPerson(
  private val personRepository: PersonRepository
) {
    operator fun invoke(person: Person): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        println("AddPersonUseCase is running")
        val result=personRepository.addPerson(person).first()
        println("AddPersonUseCase is after getting data $result")
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("Cloud Not add new person")) }
}