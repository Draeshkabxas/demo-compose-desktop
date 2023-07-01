package authorization.domain.usecase

import authorization.data.model.UserRealm
import authorization.domain.model.User
import authorization.domain.repository.AuthenticationRepository
import common.Resource
import kotlinx.coroutines.flow.*

class LoginUseCase(private val auth: AuthenticationRepository){
    operator fun invoke(user: User): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        println("login state is  loading..")
        val auth=auth.isUser(user)
        println("login state is  "+auth.first())
        emit(Resource.Success(auth.first()))
    }.catch { emit(Resource.Error("Cloud Not login")) }
}