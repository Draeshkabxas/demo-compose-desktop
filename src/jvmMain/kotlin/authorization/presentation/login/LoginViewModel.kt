package authorization.presentation.login

import androidx.compose.runtime.mutableStateOf
import authorization.data.model.User
import authorization.domain.usecase.LoginUseCase
import common.Resource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach

class LoginViewModel(
    val loginUseCase: LoginUseCase
) {
    val isLogin = mutableStateOf(false)
    fun login(user: User) {
        loginUseCase(user).onEach {
            when(it){
                is Resource.Error -> println(it.message)
                is Resource.Loading -> println("Loading...")
                is Resource.Success -> isLogin.value = false
            }
        }
    }

}