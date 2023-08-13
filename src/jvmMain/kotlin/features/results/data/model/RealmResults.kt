package features.results.data.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import utils.AgeGroup

open class RealmResults : RealmObject{
    @PrimaryKey
    var id:String = ObjectId().toHexString()
    var name: String = ""
//    var motherName: String = ""
//    var motherNationality: String = ""
//    var fileNumber: String = ""
//    var libyaId: String = ""
    var phoneNumber: String = ""
    var result: String = ""
//    var bankName: String = ""
//    var accountNumber: String = ""
//    var ageGroup:String = AgeGroup.UnderEightTeen.arabicName
//    var educationLevel: String = ""
    var date: String = ""
    var notes: String = ""
}
