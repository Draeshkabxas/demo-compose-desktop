package authorization.presentation.register

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import authorization.data.model.User
import authorization.domain.repository.AuthenticationRepository

class RegisterViewModel(private val authenticationRepository: AuthenticationRepository) {
    val currentUser = mutableStateOf<User?>(null)
    fun addUser(user: User){
        runBlocking {
            launch {
                authenticationRepository.add(user)
            }
        }
    }
}