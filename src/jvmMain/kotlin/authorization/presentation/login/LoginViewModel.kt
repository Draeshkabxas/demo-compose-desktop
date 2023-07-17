package authorization.presentation.login

import androidx.compose.runtime.mutableStateOf
import authorization.domain.model.Jobs
import authorization.domain.model.Jobs.None
import authorization.domain.model.User
import authorization.domain.usecase.LoginUseCase
import utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.compose.getKoin
import org.koin.core.context.GlobalContext.get
import utils.UserAuthSystem

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) {
    val loginState = mutableStateOf<Resource<out User?>>(Resource.Loading(User("","","", None, emptyList())))
    fun login(user: User) {
        println("work in login $user")
        loginUseCase.invoke(user)
            .onEach {
                    loginState.value = it
        }.launchIn(CoroutineScope(Dispatchers.IO))

    }

    fun setupUserAsCurrentUser(user: User){
        val userAuthSystem:UserAuthSystem = get().get()
        userAuthSystem.currentUser = user
    }

}