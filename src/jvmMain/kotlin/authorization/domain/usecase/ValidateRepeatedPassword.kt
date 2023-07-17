package authorization.domain.usecase

import authorization.domain.model.ValidationResult

class ValidateRepeatedPassword {

    fun execute(password: String, repeatedPassword: String): ValidationResult {
        if(password != repeatedPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "كلمات المرور غير متطابقة"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}
