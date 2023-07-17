package features.sons_of_officers.domain.usecases

import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import utils.Resource

//class RemoveAllPersons(
//    private val personRepository: PersonRepository
//) {
//    operator fun invoke(): Flow<Resource<Boolean>> = flow{
//        emit(Resource.Loading(data = true))
//        val result=personRepository.removeAllPersons().first()
//        emit(Resource.Success(result))
//    }.catch { emit(Resource.Error("Cloud Not remove all contract")) }
//}