package authorization.domain.usecase

import authorization.domain.model.ValidationResult

class ValidatePassword {

    fun execute(password: String): ValidationResult {
        if(password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "يجب أن تتكون كلمة المرور من 8 أحرف على الأقل"
            )
        }
        val containsLettersAndDigits = password.any { it.isDigit() } &&
                password.any { it.isLetter() }
        if(!containsLettersAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "يجب أن تحتوي كلمة المرور على حرف واحد على الأقل"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}
