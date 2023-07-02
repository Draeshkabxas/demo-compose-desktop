package features.sons_of_officers.domain.usecases

import authorization.domain.model.ValidationResult

class ValidateTextInputs {


    fun execute(text: String,name:String,canHaveNumbers:Boolean = false): ValidationResult {
        if(text.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The $name can not be empty"
            )
        }
        val containsJustDigits = text.all { it ==' ' || if(canHaveNumbers)  it.isLetterOrDigit() else it.isLetter()  }
        if(!containsJustDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "The $name should its characters be all in letters"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}