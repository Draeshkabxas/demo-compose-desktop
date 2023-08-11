package features.courses.domain.usecases

import utils.Resource
import features.courses.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class RemoveAllCourses(
    private val coursesRepository: CoursesRepository
){
    operator fun invoke(): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        val result=coursesRepository.removeAll().first()
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("Cloud Not remove all courses")) }
}