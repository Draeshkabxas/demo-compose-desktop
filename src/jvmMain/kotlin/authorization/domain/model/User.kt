package authorization.domain.model

data class User(
    val id:String,
    val name:String,
    val password:String,
    var job:Jobs,
    var systems: List<Systems>
)