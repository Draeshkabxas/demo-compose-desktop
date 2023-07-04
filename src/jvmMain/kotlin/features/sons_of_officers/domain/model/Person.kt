package features.sons_of_officers.domain.model

data class Person(
    val id:String,
    val name:String,
    val motherName:String,
    val fileNumber:String,
    val libyaId:String,
    val phoneNumber:String,
    val educationLevel:String,
    val recruiter:String,
    val city:String,
    val justificationsRequire:Map<String,Boolean>,
    val procedures:Map<String,Boolean>
)