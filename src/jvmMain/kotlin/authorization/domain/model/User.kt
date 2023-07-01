package authorization.domain.model

data class User(
    val id:String,
    val name:String,
    val password:String,
    val job:Jobs
)