package features.results.data.dto


import features.results.data.model.RealmResults
import features.results.domain.model.Results
import utils.fromArabicNameToAgeGroup

fun RealmResults.toResultsDto(): Results {
    val realmResults = this
    return Results(
        id = this.id,
        name = this.name,
//        motherName = this.motherName,
//        motherNationality = this.motherNationality,
//        fileNumber = this.fileNumber,
//        libyaId = this.libyaId,
        phoneNumber = this.phoneNumber,
        result = this.result,
//        bankName = this.bankName,
//        accountNumber = this.accountNumber,
//        educationLevel = this.educationLevel,
        date = this.date,
//        ageGroup=this.ageGroup.fromArabicNameToAgeGroup(),
        notes = this.notes
    )
}