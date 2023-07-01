package authorization.domain.usecase

import authorization.domain.model.ValidationResult
import authorization.domain.repository.AuthenticationRepository

class ValidateUsername(
    private val auth: AuthenticationRepository
) {

    fun execute(username: String): ValidationResult {
        if(username.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The name can't be blank"
            )
        }
        if(username.length < 4 ) {
            return ValidationResult(
                successful = false,
                errorMessage = "That's not a valid username"
            )
        }
        if(auth.isThereUserWithName(username)) {
            return ValidationResult(
                successful = false,
                errorMessage = "The user is already exist"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}