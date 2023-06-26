package authorization.presentation.register

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.Flow
import authorization.data.model.User
import authorization.domain.repository.AuthenticationRepository
import authorization.domain.usecase.SignupUseCase
import io.realm.kotlin.internal.util.CoroutineDispatcherFactory
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RegisterViewModel(private val signupUseCase: SignupUseCase) {
    fun signup(user: User){
        println("sign up work")
        signupUseCase.invoke(user).onEach {
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }
}