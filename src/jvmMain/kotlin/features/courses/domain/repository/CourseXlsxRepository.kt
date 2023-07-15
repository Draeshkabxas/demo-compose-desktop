package features.courses.domain.repository

import features.courses.domain.model.Course
import features.sons_of_officers.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface CourseXlsxRepository {
    fun printCoursesToXlsxFile(courses:List<Course>, filePath:String, headers:List<String>): Flow<Boolean>
    fun getCoursesFromXlsxFile(path:String):Flow<List<Course>>
}