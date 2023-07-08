package features.courses.domain.usecases

import common.Resource
import features.courses.domain.model.Course
import features.courses.domain.repository.CoursesRepository
import features.sons_of_officers.domain.model.Person
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class AddCourse(
  private val coursesRepository: CoursesRepository
) {
    operator fun invoke(course: Course): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        println("AddPersonUseCase is running")
        val result=coursesRepository.addCourse(course).first()
        println("AddPersonUseCase is after getting data $result")
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("Cloud Not add new person")) }
}