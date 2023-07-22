package features.sons_of_officers.domain.usecases

import utils.Resource
import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class RemovePerson(
  private val personRepository: PersonRepository
) {
    operator fun invoke(removedPerson: Person): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        println("remove person is running $removedPerson")
        val result=personRepository.removePerson(removedPerson).first()
        println("remove person after getting data $result")
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("Cloud Not remove this person")) }
}