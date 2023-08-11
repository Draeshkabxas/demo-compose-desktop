package features.results.data.dto


import features.results.data.model.RealmResults
import features.results.domain.model.Results
import org.mongodb.kbson.ObjectId

fun Results.toRealmResultsDto(): RealmResults {
    val results = this
    return RealmResults().apply {
        id = this.id.ifEmpty { ObjectId().toHexString() }
        name = this.name
//        motherName = contract.motherName
//        motherNationality = contract.motherNationality
//        fileNumber = contract.fileNumber
//        libyaId = contract.libyaId
        phoneNumber = this.phoneNumber
        result = this.result
//        bankName = contract.bankName
//        accountNumber = contract.accountNumber
//        educationLevel = contract.educationLevel
        date = this.date
//        ageGroup = contract.ageGroup.arabicName
        notes = this.notes
    }
}