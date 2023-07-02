package features.sons_of_officers.domain.usecases

import authorization.domain.model.ValidationResult

class ValidateLibyaId {
    fun execute(libyaId: String): ValidationResult {
        if(libyaId.length != 12) {
            return ValidationResult(
                successful = false,
                errorMessage = "The libya id needs to be 12 digits"
            )
        }
        val containsJustDigits = libyaId.all { it.isDigit() }
        if(!containsJustDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "The libya id should its characters be all digits"
            )
        }
        if (libyaId.first() != '1'){
            return ValidationResult(
                successful = false,
                errorMessage = "The libya id of a man should start with number 1"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}