package features.contracts.data.dto

import features.contracts.data.model.RealmContract
import features.contracts.domain.model.Contract

import utils.fromArabicNameToAgeGroup

fun RealmContract.toContractDto(): Contract {
    val realmContract = this
    return Contract(
        id = this.id,
        name = this.name,
        motherName = this.motherName,
        motherNationality = this.motherNationality,
        fileNumber = this.fileNumber,
        libyaId = this.libyaId,
        phoneNumber = this.phoneNumber,
        dependency = this.dependency,
        bankName = this.bankName,
        accountNumber = this.accountNumber,
        educationLevel = this.educationLevel,
        city = this.city,
        ageGroup=this.ageGroup.fromArabicNameToAgeGroup(),
        notes = this.notes
    )
}