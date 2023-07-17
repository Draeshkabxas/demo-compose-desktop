package features.sons_of_officers.domain.usecases

import utils.Resource
import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class UpdatePerson(
  private val personRepository: PersonRepository
) {
    operator fun invoke(updatedPerson: Person): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        println("Update person is running $updatedPerson")
        val result=personRepository.updatePerson(updatedPerson).first()
        println("Update person after getting data $result")
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("Cloud Not update this person")) }
}