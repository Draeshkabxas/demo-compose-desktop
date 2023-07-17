package authorization.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import authorization.domain.model.Jobs
import authorization.domain.model.User
import authorization.domain.usecase.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

class RegisterViewModel(
    private val signupUseCase: SignupUseCase,
    private val validateEmail: ValidateUsername,
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validateRepeatedPassword: ValidateRepeatedPassword = ValidateRepeatedPassword(),
    private val validateTerms: ValidateTerms = ValidateTerms(),
    private val closeApplication: CloseApplication
) {
    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun closeApp(){
        closeApplication()
    }

    fun onEvent(event: RegistrationFormEvent){
        when(event){
            is RegistrationFormEvent.UsernameChanged -> {
                state = state.copy(username = event.username)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegistrationFormEvent.RepeatedPasswordChanged -> {
                state = state.copy(repeatedPassword = event.repeatedPassword)
            }
            is RegistrationFormEvent.AcceptTerms -> {
                state = state.copy(acceptedTerms = event.isAccepted)
            }
            RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }


    private fun submitData() {
        val userResult = validateEmail.execute(state.username)
        val passwordResult = validatePassword.execute(state.password)
        val repeatedPasswordResult = validateRepeatedPassword.execute(
            state.password, state.repeatedPassword
        )
        val termsResult = validateTerms.execute(state.acceptedTerms)

        val hasError = listOf(
            userResult,
            passwordResult,
            repeatedPasswordResult,
            termsResult
        ).any { !it.successful }

        if(hasError) {
            state = state.copy(
                usernameError = userResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatedPasswordResult.errorMessage,
                termsError = termsResult.errorMessage
            )
            return
        }
        signupUseCase.invoke(User("", state.username, state.password, Jobs.None, listOf())).onEach {
            validationEventChannel.send(ValidationEvent.Success)
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    sealed class ValidationEvent {
        object Success: ValidationEvent()
    }
}