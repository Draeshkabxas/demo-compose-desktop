package features.contracts.data.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import utils.AgeGroup

open class RealmContract : RealmObject{
    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var motherName: String = ""
    var motherNationality: String = ""
    var fileNumber: String = ""
    var libyaId: String = ""
    var phoneNumber: String = ""
    var dependency: String = ""
    var bankName: String = ""
    var accountNumber: String = ""
    var ageGroup:String = AgeGroup.UnderEightTeen.arabicName
    var educationLevel: String = ""
    var city: String = ""
    var notes: String = ""
}
