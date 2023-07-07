package features.sons_of_officers.data.repository

import features.sons_of_officers.data.dto.toPersonDTO
import features.sons_of_officers.data.dto.toRealmPerson
import features.sons_of_officers.data.model.RealmPerson
import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.domain.repository.PersonRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RealmPersonImpl(private val realm: Realm) :
    PersonRepository {
    init {
        Runtime.getRuntime().addShutdownHook(Thread {
            realm.close()
        })
    }

    override fun getAllPeople(filterQuery: String): Flow<List<Person>> {
        return realm.query<RealmPerson>().asFlow()
            .map {
                it.list.map { realmPerson -> realmPerson.toPersonDTO() }
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

    override fun getPerson(id: String): Flow<Person?> = flow{
        val data = realm.query<RealmPerson>("id == $0",id).first().find()
        if (data == null) {
            emit(null)
        } else {
            emit(data.toPersonDTO())
        }
    }

    override fun updatePerson(person: Person): Flow<Boolean> = flow{
        val oldPerson:Person? = getPerson(person.id).first()
        val updatedPerson = person.toRealmPerson()
        var result = false
        if (oldPerson == null) emit(false)
        realm.writeBlocking {
            oldPerson?.let {
                findLatest(it.toRealmPerson())?.apply {
                    name = updatedPerson.name
                    motherName = updatedPerson.motherName
                    fileNUmber= updatedPerson.phoneNUmber
                    libyaId=updatedPerson.libyaId
                    phoneNUmber=updatedPerson.phoneNUmber
                    educationLevel = updatedPerson.educationLevel
                    recruiter=updatedPerson.recruiter
                    city= updatedPerson.city
                    ageGroup = updatedPerson.ageGroup
                    justificationsRequire = updatedPerson.justificationsRequire
                    procedures = updatedPerson.procedures
                }
            }
            result = true
        }
        emit(result)
    }

}