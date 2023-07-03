package features.sons_of_officers.domain.usecases

import authorization.domain.model.ValidationResult

class ValidateQuadrupleName {

    fun execute(name: String): ValidationResult {
        if(name.split(" ").size < 4) {
            return ValidationResult(
                successful = false,
                errorMessage = "يجب ان يكون الإسم رباعي"
            )
        }
        val containsJustDigits = name.all { it.isLetter() || it==' ' }
        if(!containsJustDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "لايمكن ان يحتوي الاسم على ارقام"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}