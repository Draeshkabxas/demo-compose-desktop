package features.contracts.data.dto

import features.contracts.data.model.RealmContract
import features.contracts.domain.model.Contract
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

fun Contract.toRealmContractDto(): RealmContract {
    val contract = this
    return RealmContract().apply {
        id = contract.id.ifEmpty { ObjectId().toHexString() }
        name = contract.name
        motherName = contract.motherName
        motherNationality = contract.motherNationality
        fileNumber = contract.fileNumber
        libyaId = contract.libyaId
        phoneNumber = contract.phoneNumber
        dependency = contract.dependency
        bankName = contract.bankName
        accountNumber = contract.accountNumber
        educationLevel = contract.educationLevel
        city = contract.city
        ageGroup = contract.ageGroup.arabicName
        notes = contract.notes
        reference =contract.reference
    }
}