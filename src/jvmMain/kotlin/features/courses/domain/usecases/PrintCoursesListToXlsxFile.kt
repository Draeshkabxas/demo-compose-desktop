package features.courses.domain.usecases

import common.Resource
import features.courses.domain.model.Course
import features.courses.domain.repository.CourseXlsxRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class PrintCoursesListToXlsxFile(
    private val courseXlsxRepository: CourseXlsxRepository
) {
    operator fun invoke(courses: List<Course>, filePath:String, headers:List<String>): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        println("persons have data $courses")
        val result=courseXlsxRepository.printCoursesToXlsxFile(courses,filePath,headers)
        println("print to xlsx state  "+result.first())
        emit(Resource.Success(result.first()))
    }.catch { emit(Resource.Error("Cloud Not print to xlsx file")) }
}