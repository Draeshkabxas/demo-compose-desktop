package features.sons_of_officers.domain.usecases

import authorization.domain.model.ValidationResult

class ValidateLibyaId {
    fun execute(libyaId: String): ValidationResult {
        if(libyaId.length != 12) {
            return ValidationResult(
                successful = false,
                errorMessage = "الرقم الوطني يجب ان يحتوي على 13 رقم"
            )
        }
        val containsJustDigits = libyaId.all { it.isDigit() }
        if(!containsJustDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "The libya id should its characters be all digits"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}