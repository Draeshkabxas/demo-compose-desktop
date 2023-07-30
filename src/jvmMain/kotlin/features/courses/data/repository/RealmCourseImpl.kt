package features.courses.data.repository

import features.contracts.data.model.RealmContract
import features.courses.data.dto.toCourseDTO
import features.courses.data.dto.toRealmCourse
import features.courses.data.model.RealmCourse
import features.courses.domain.model.Course
import features.courses.domain.repository.CoursesRepository
import features.sons_of_officers.data.model.RealmPerson
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RealmCourseImpl(private val realm: Realm) :
    CoursesRepository {


    override fun getAllCourses(filterQuery: String): Flow<List<Course>> {
        return realm.query<RealmCourse>()
            .asFlow()
            .map {
                it.list.map { realmCourse -> realmCourse.toCourseDTO() }.reversed()
            }
    }

    override fun addCourse(person: Course): Flow<Boolean> = flow {
        println("AddCourseImpl is running")
        var result = true
       realm.writeBlocking {
            try {
                println("AddCourseImpl in try catch")
                copyToRealm(person.toRealmCourse())
            } catch (e: Exception) {
                println("AddCourseImpl in error catch")
                println(e.localizedMessage)
                result = false
            }
        }
        emit(result)
    }

    override fun getCourse(id: String): Flow<Course?> = flow{
        val data = realm.query<RealmCourse>("id == $0",id).first().find()
        if (data == null) {
            emit(null)
        } else {
            emit(data.toCourseDTO())
        }
    }
    private fun getCourse(course: Course): RealmCourse? {
      return realm.query<RealmCourse>("id = $0", course.id)
            .first()
            .find()
    }

    override fun updateCourse(person: Course): Flow<Boolean> = flow{
        var result = false
        try {
            realm.writeBlocking {
                val updatedPerson = person.toRealmCourse()
                query<RealmCourse>("id = $0", person.id)
                    .first()
                    .find()                    ?.also { oldPerson ->
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
            println("Realm update course impl has error ${e.localizedMessage}")
        }
        emit(result)
    }

    override fun removeAll(): Flow<Boolean> = flow {
        var result = true
        realm.writeBlocking {
            try {
                delete(RealmCourse::class)
            }catch (e:Exception){
                result = false
            }
        }
        emit(result)
    }

    override fun removeCourse(course: Course): Flow<Boolean>  = flow {
        var result = false
        try {
            realm.writeBlocking {
                getCourse(course)?.also {
                    findLatest(it)?.also {removedCourse->
                        delete(removedCourse)
                    }
                }
                result = true
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        emit(result)
    }

}