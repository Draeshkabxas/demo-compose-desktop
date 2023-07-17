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
                errorMessage = "اسم المستخدم لايمكن ان يكون فارغا"
            )
        }
        if(username.length < 4 ) {
            return ValidationResult(
                successful = false,
                errorMessage = "هذا ليس اسم مستخدم صالح"
            )
        }
        if(auth.isThereUserWithName(username)) {
            return ValidationResult(
                successful = false,
                errorMessage = "المستخدم موجود بالفعل"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}