package authorization.domain.usecase

import authorization.data.model.User
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    operator fun invoke(user:User): Flow<Boolean>
}