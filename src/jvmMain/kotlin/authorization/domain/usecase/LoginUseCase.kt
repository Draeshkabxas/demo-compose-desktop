package authorization.domain.usecase

import authorization.data.model.User
import authorization.domain.repository.AuthenticationRepository
import common.Resource
import kotlinx.coroutines.flow.*

class LoginUseCase(private val auth: AuthenticationRepository){
    operator fun invoke(user: User): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading())
        val auth=auth.isUser(user)
        emit(Resource.Success(auth.first()))
    }.catch { emit(Resource.Error("Cloud Not login")) }
}