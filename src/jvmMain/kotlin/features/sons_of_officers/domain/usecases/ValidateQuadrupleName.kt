package features.sons_of_officers.domain.usecases

import authorization.domain.model.ValidationResult

class ValidateQuadrupleName {

    fun execute(name: String): ValidationResult {
        if(name.split(" ").size < 4) {
            return ValidationResult(
                successful = false,
                errorMessage = "The name needs to be 4 words"
            )
        }
        val containsJustDigits = name.all { it.isLetter() || it==' ' }
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