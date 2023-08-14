package features.contracts.domain.model

import utils.AgeGroup

data class Contract(
    val id:String,
    var name:String,
    val motherName:String,
    val motherNationality:String,
    val fileNumber:String,
    val libyaId:String,
    val phoneNumber:String,
    val dependency:String,
    val bankName:String,
    val accountNumber:String,
    val educationLevel:String,
    val city:String,
    val ageGroup: AgeGroup,
    val notes:String,
    val reference:String
)
