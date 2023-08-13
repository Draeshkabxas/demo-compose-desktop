package features.results.data.dto


import features.results.data.model.RealmResults
import features.results.domain.model.Results
import utils.fromArabicNameToAgeGroup

fun RealmResults.toResultsDto(): Results {
    val realmResults = this
    return Results(
        id = realmResults.id,
        name = realmResults.name,
//        motherName = this.motherName,
//        motherNationality = this.motherNationality,
//        fileNumber = this.fileNumber,
//        libyaId = this.libyaId,
        phoneNumber = realmResults.phoneNumber,
        result = realmResults.result,
//        bankName = this.bankName,
//        accountNumber = this.accountNumber,
//        educationLevel = this.educationLevel,
        date = realmResults.date,
//        ageGroup=this.ageGroup.fromArabicNameToAgeGroup(),
        notes = realmResults.notes
    )
}