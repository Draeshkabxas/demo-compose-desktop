package authorization.domain.usecase

import authorization.domain.model.User
import authorization.domain.repository.AuthenticationRepository
import utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class UpdateUser (
    private val authRepo: AuthenticationRepository
) {
    operator fun invoke(updatedUser: User): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        println("Update User is running $updatedUser")
        val result=authRepo.updateUser(updatedUser).first()
        println("Update User after getting data $result")
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("Cloud Not update this User")) }
}