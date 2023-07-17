package authorization.domain.usecase

import authorization.domain.model.User
import authorization.domain.repository.AuthenticationRepository
import utils.Resource
import kotlinx.coroutines.flow.*

class LoginUseCase(private val auth: AuthenticationRepository){
    operator fun invoke(user: User): Flow<Resource<out User?>> = flow{
        emit(Resource.Loading(null))
        println("login state is  loading..")
        val result=auth.isUser(user).first()
        println("login state is  $result")
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("Cloud Not login")) }
}