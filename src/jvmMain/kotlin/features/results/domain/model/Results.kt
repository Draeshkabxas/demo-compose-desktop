package features.results.domain.model

import utils.AgeGroup

data class Results(
    val id:String,
    var name:String,
//    val motherName:String,
//    val motherNationality:String,
//    val fileNumber:String,
//    val libyaId:String,
    val phoneNumber:String,
    val result:String,
//    val bankName:String,
//    val accountNumber:String,
//    val educationLevel:String,
    val date:String,
//    val ageGroup: AgeGroup,
    val notes:String
)
