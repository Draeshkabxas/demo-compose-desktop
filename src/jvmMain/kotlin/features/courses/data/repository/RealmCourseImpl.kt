package features.courses.data.repository

import features.courses.data.dto.toCourseDTO
import features.courses.data.dto.toRealmCourse
import features.courses.data.model.RealmCourse
import features.courses.domain.model.Course
import features.courses.domain.repository.CoursesRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RealmCourseImpl(private val realm: Realm) :
    CoursesRepository {
    init {
        Runtime.getRuntime().addShutdownHook(Thread {
            realm.close()
        })
    }

    override fun getAllCourses(filterQuery: String): Flow<List<Course>> {
        return realm.query<RealmCourse>().asFlow()
            .map {
                it.list.map { realmCourse -> realmCourse.toCourseDTO() }
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

    override fun updateCourse(person: Course): Flow<Boolean> = flow{
        val oldCourse:Course? = getCourse(person.id).first()
        val updatedCourse = person.toRealmCourse()
        var result = false
        if (oldCourse == null) emit(false)
        realm.writeBlocking {
            oldCourse?.let {
                findLatest(it.toRealmCourse())?.apply {
                    name = updatedCourse.name
                    motherName = updatedCourse.motherName
                    fileNUmber= updatedCourse.phoneNUmber
                    libyaId=updatedCourse.libyaId
                    phoneNUmber=updatedCourse.phoneNUmber
                    educationLevel = updatedCourse.educationLevel
                    recruiter=updatedCourse.recruiter
                    city= updatedCourse.city
                    ageGroup = updatedCourse.ageGroup
                    justificationsRequire = updatedCourse.justificationsRequire
                    procedures = updatedCourse.procedures
                }
            }
            result = true
        }
        emit(result)
    }

}