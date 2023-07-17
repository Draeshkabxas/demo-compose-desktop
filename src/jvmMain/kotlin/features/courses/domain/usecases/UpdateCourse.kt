package features.courses.domain.usecases

import utils.Resource
import features.courses.domain.model.Course
import features.courses.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class UpdateCourse(
    private val coursesRepository: CoursesRepository
) {
    operator fun invoke(updatedCourse: Course): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        println("Update Course is running $updatedCourse")
        val result=coursesRepository.updateCourse(updatedCourse).first()
        println("Update Course after getting data $result")
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("Cloud Not update this Course")) }
}