package features.sons_of_officers.domain.usecases

import authorization.domain.model.ValidationResult

class ValidateTextInputs {


    fun execute(text: String,name:String): ValidationResult {
        val containsJustDigits = text.all { it.isLetter() }
        if(!containsJustDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "The name should its characters be all in letters"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}