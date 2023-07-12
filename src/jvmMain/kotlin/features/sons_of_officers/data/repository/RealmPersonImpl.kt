package features.sons_of_officers.data.repository

import features.sons_of_officers.data.dto.toPersonDTO
import features.sons_of_officers.data.dto.toRealmPerson
import features.sons_of_officers.data.model.RealmPerson
import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.domain.repository.PersonRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.*

class RealmPersonImpl(private val realm: Realm) :
    PersonRepository {
    init {
        Runtime.getRuntime().addShutdownHook(Thread {
            realm.close()
        })
    }

    override fun getAllPeople(filterQuery: String): Flow<List<Person>> {
        return realm
            .query<RealmPerson>()
            .asFlow()
            .map {
                it.list.map { realmPerson -> realmPerson.toPersonDTO() }.reversed()
            }
    }

    override fun addPerson(person: Person): Flow<Boolean> = flow {
        println("AddPersonImpl is running")
        var result = true
        realm.writeBlocking {
            try {
                println("AddPersonImpl in try catch")
                copyToRealm(person.toRealmPerson())
            } catch (e: Exception) {
                println("AddPersonImpl in error catch")
                println(e.localizedMessage)
                result = false
            }
        }
        emit(result)
    }

    override fun getPerson(id: String): Flow<Person?> =flow {
        emit(
            realm.query<RealmPerson>("id = $0", id)
            .first()
                .find()?.toPersonDTO()
        )
    }

    override fun updatePerson(person: Person): Flow<Boolean> = flow {
        var result = false
        try {
            realm.writeBlocking {
                val updatedPerson = person.toRealmPerson()
                query<RealmPerson>("id = $0", person.id)
                    .first()
                    .find()
                    ?.also { oldPerson ->
                        findLatest(oldPerson.apply {
                            name = updatedPerson.name
                            motherName = updatedPerson.motherName
                            fileNUmber = updatedPerson.fileNUmber
                            libyaId = updatedPerson.libyaId
                            phoneNUmber = updatedPerson.phoneNUmber
                            educationLevel = updatedPerson.educationLevel
                            recruiter = updatedPerson.recruiter
                            city = updatedPerson.city
                            ageGroup = updatedPerson.ageGroup
                            justificationsRequire = updatedPerson.justificationsRequire
                            procedures = updatedPerson.procedures
                        })
                    }
                result = true
            }
        } catch (e: Exception) {
            println("Realm update person impl has error ${e.localizedMessage}")
        }
        emit(result)
    }

}