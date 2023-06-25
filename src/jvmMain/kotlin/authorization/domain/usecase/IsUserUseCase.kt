package authorization.domain.usecase

import authorization.data.model.User
import authorization.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

class IsUserUseCase(private val authenticationRepository: AuthenticationRepository):LoginUseCase {
    override operator fun invoke(user: User): Flow<Boolean> =
        authenticationRepository.isUser(user)
}