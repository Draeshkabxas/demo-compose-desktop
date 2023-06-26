package authorization.presentation.login

import androidx.compose.runtime.mutableStateOf
import authorization.data.model.User
import authorization.domain.usecase.LoginUseCase
import common.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) {
    val isLogin = mutableStateOf(false)
    fun login(user: User) {
        println("work in login $user")
        loginUseCase.invoke(user)
            .onEach {
            when(it){
                is Resource.Error -> println(it.message)
                is Resource.Loading -> println("Loading...")
                is Resource.Success -> isLogin.value = true
            }
            println("message: ${it.message} : data: ${it.data}")
        }.launchIn(CoroutineScope(Dispatchers.IO))

    }

}