package authorization.domain.usecase

import authorization.data.model.User
import authorization.domain.repository.AuthenticationRepository
import common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class SignupUseCase(private val auth: AuthenticationRepository) {
    operator fun invoke(user: User): Flow<Resource<User>> = flow {
        println("Signup working")
        emit(Resource.Loading())
        val isUser = auth.isUser(user).first()
        if (!isUser) {
             auth.add(user)
            emit(Resource.Success(user))
            println(auth.getUser(user).first().toString())
        } else {
            emit(Resource.Error("User Already exists"))
        }
    }.catch { emit(Resource.Error("Cloud Not login")) }
}