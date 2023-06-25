package authorization.presentation.register

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import authorization.data.model.User
import authorization.domain.repository.AuthenticationRepository
import authorization.domain.usecase.SignupUseCase

class RegisterViewModel(private val signupUseCase: SignupUseCase) {
    fun signup(user: User){
        runBlocking {
            launch {
                signupUseCase(user)
            }
        }
    }
}