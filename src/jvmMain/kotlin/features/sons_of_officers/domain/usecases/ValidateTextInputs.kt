package features.sons_of_officers.domain.usecases

import authorization.domain.model.ValidationResult

class ValidateTextInputs {


    fun execute(text: String,name:String,canHaveNumbers:Boolean = false): ValidationResult {
        if(text.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = " $name لا يمكن ان يكون فارغا "
            )
        }
        val containsJustDigits = text.all { it ==' ' || if(canHaveNumbers)  it.isLetterOrDigit() else it.isLetter()  }
//        if(!containsJustDigits) {
//            return ValidationResult(
//                successful = false,
//                errorMessage = " $name يجب ان يحتوي على حروف فقط "
//            )
//        }
        return ValidationResult(
            successful = true
        )
    }
}