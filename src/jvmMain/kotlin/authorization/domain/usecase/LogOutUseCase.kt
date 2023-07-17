package authorization.domain.usecase

import authorization.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import utils.Resource

class LogoutUseCase(private val auth: AuthenticationRepository) {
    operator fun invoke(): Flow<Resource<out Unit>> = flow {
        emit(Resource.Loading(null))
        println("LogoutUseCase is running")
        auth.logout()
        emit(Resource.Success(Unit))
    }.catch { emit(Resource.Error("Could not logout")) }
}