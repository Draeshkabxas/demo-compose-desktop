package features.results.data.dto


import features.results.data.model.RealmResults
import features.results.domain.model.Results
import org.mongodb.kbson.ObjectId

fun Results.toRealmResultsDto(): RealmResults {
    val results = this
    return RealmResults().apply {
        id = results.id.ifEmpty { ObjectId().toHexString() }
        name = results.name
//        motherName = contract.motherName
//        motherNationality = contract.motherNationality
//        fileNumber = contract.fileNumber
//        libyaId = contract.libyaId
        phoneNumber = results.phoneNumber
        result = results.result
//        bankName = contract.bankName
//        accountNumber = contract.accountNumber
//        educationLevel = contract.educationLevel
        date = results.date
//        ageGroup = contract.ageGroup.arabicName
        notes = results.notes
    }
}