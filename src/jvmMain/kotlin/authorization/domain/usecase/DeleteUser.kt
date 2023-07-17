package authorization.domain.usecase

import authorization.domain.model.User
import authorization.domain.repository.AuthenticationRepository
import utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class DeleteUser (
    private val authRepo: AuthenticationRepository
) {
    operator fun invoke(deletedUser: User): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        println("Delete User is running $deletedUser")
        val result=authRepo.deleteUser(deletedUser).first()
        println("Delete User after getting data $result")
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("Cloud Not delete this user ${deletedUser.name}")) }
}