package authorization.domain.usecase

import authorization.domain.model.ValidationResult

class ValidateTerms {

    fun execute(acceptedTerms: Boolean): ValidationResult {
        if(!acceptedTerms) {
            return ValidationResult(
                successful = false,
                errorMessage = "الرجاء قبول الشروط"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}
