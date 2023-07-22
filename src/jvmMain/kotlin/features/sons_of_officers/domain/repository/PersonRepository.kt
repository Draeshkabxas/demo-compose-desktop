package features.sons_of_officers.domain.repository

import features.sons_of_officers.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface PersonRepository {

    fun addPerson(person: Person): Flow<Boolean>
    fun getAllPeople(filterQuery: String): Flow<List<Person>>
    fun getPerson(id: String): Flow<Person?>
    fun updatePerson(person: Person): Flow<Boolean>
    fun removePerson(person: Person): Flow<Boolean>
    fun removeAll(): Flow<Boolean>
}