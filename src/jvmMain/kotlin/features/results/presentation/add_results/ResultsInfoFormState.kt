package features.results.presentation.add_results

import features.results.domain.model.Results

data class ResultsInfoFormState(
    val id:String ="",
    val name: String = "",
    val nameError: String? = null,
//    val motherName: String = "",
//    val motherNameError: String? = null,
//    val motherNationality:String ="",
//    val motherNationalityError:String? = null,
//    val fileNumber: String = "",
//    val fileNumberError: String? = null,
//    val libyaId: String = "",
//    val libyaIdError: String? = null,
    val phoneNumber: String = "",
    val phoneNumberError: String? = null,
    val result:String ="",
    val resultError:String?=null,
//    val educationLevel: String = "",
//    val educationLevelError: String? = null,
//    val bankName: String = "",
//    val bankNameError: String? = null,
//    val accountNumber: String = "",
//    val accountNumberError: String? = null,
    val date: String = "",
    val dateError: String? = null,
    val notes: String = "",
    val notesError: String? = null,
)
fun Results.toResultsInfoFormState(): ResultsInfoFormState =
    ResultsInfoFormState(
        id = this.id,
        name = this.name,
//        motherName = this.motherName,
//        motherNationality = this.motherNationality,
//        fileNumber = this.fileNumber,
//        libyaId = this.libyaId,
        phoneNumber = this.phoneNumber,
//        educationLevel = this.educationLevel,
//        bankName = this.bankName,
//        accountNumber = this.accountNumber,
        result = this.result,
        date = this.date,
        notes =this.notes
    )