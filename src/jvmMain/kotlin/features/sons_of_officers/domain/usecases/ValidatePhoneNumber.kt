package features.sons_of_officers.domain.usecases

import authorization.domain.model.ValidationResult

class ValidatePhoneNumber {

    fun execute(phoneNumber: String): ValidationResult {
        if(phoneNumber.length != 9) {
            return ValidationResult(
                successful = false,
                errorMessage = "The phone number needs to be 9 digits"
            )
        }
        val containsJustDigits = phoneNumber.all { it.isDigit() }
        if(!containsJustDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "The phone number should its characters be all in digits"
            )
        }
        if (phoneNumber.startsWith("09")){
            return ValidationResult(
                successful = false,
                errorMessage = "The phone number should start with 09"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}