package authorization.presentation.login

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import authorization.data.model.User
import authorization.domain.repository.AuthenticationRepository
import authorization.domain.usecase.IsUserUseCase

class LoginViewModel(
    val isUserUseCase: IsUserUseCase
) {
    fun isUser(user: User) = isUserUseCase(user)

}