package authorization.domain.usecase

import authorization.data.model.UsersRealm
import authorization.domain.model.User
import authorization.domain.repository.AuthenticationRepository
import common.Resource
import kotlinx.coroutines.flow.*

class LoginUseCase(private val auth: AuthenticationRepository){
    operator fun invoke(user: User): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        println("login state is  loading..")
        val auth=auth.isUser(user).first()
        println("login state is  $auth")
        emit(Resource.Success(auth))
    }.catch { emit(Resource.Error("Cloud Not login")) }
}