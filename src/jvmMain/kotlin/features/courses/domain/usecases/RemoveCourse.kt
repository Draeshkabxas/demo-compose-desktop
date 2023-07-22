package features.courses.domain.usecases

import utils.Resource
import features.courses.domain.model.Course
import features.courses.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class RemoveCourse(
    private val coursesRepository: CoursesRepository
) {
    operator fun invoke(removedCourse: Course): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        println("remove Course is running $removedCourse")
        val result=coursesRepository.removeCourse(removedCourse).first()
        println("remove Course after getting data $result")
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("Cloud Not remove this Course")) }
}