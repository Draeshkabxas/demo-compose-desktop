package features.contracts.data.dto

import features.contracts.data.model.RealmContract
import features.contracts.domain.model.Contract
import utils.fromArabicNameToAgeGroup

fun RealmContract.toContractDto(): Contract {
    val realmContract = this
    return Contract(
        id = realmContract.id.toHexString(),
        name = realmContract.name,
        motherName = realmContract.motherName,
        motherNationality = realmContract.motherNationality,
        fileNumber = realmContract.fileNumber,
        libyaId = realmContract.libyaId,
        phoneNumber = realmContract.phoneNumber,
        dependency = realmContract.dependency,
        bankName = realmContract.bankName,
        accountNumber = realmContract.accountNumber,
        educationLevel = realmContract.educationLevel,
        city = realmContract.city,
        ageGroup=realmContract.ageGroup.fromArabicNameToAgeGroup(),
        notes = realmContract.notes
    )
}