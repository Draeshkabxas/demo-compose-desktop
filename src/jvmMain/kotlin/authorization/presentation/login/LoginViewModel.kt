package authorization.presentation.login

import androidx.compose.runtime.mutableStateOf
import authorization.data.model.UsersRealm
import authorization.domain.model.User
import authorization.domain.usecase.LoginUseCase
import common.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) {
    val loginState = mutableStateOf<Resource<Boolean>>(Resource.Loading())
    fun login(user: User) {
        println("work in login $user")
        loginUseCase.invoke(user)
            .onEach {
                loginState.value = it
        }.launchIn(CoroutineScope(Dispatchers.IO))

    }

}