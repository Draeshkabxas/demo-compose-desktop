package features.courses.domain.repository

import features.courses.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {

    fun addCourse(person: Course): Flow<Boolean>
    fun getAllCourses(filterQuery:String):Flow<List<Course>>
     fun getCourse(id:String):Flow<Course?>
     fun updateCourse(person: Course):Flow<Boolean>
}