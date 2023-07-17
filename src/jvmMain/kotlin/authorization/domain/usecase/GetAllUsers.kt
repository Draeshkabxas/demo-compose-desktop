package authorization.domain.usecase

import authorization.domain.model.User
import authorization.domain.repository.AuthenticationRepository
import utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class GetAllUsers(
    private val authRepo:AuthenticationRepository
) {
    operator fun invoke(): Flow<Resource<List<User>>> = flow{
        emit(Resource.Loading(data = emptyList()))
        val result= authRepo.getAllUsers()
        val resultAfterFiltered = result.first()
        emit(Resource.Success(resultAfterFiltered))
    }.catch { emit(Resource.Error("Cloud Not add new person")) }
}
