package authorization.domain.model

data class User(
    val id: String,
    val name: String,
    val password: String,
    var job: Jobs,
    var systems: List<Systems>
)

fun User.changeJob(newJob: Jobs) {
    job = newJob
}

fun User.canAccessScreen(screen: Systems) = systems.contains(screen)

fun User.addOrRemoveScreenFromSystem(systems: Systems, addOrRemove: Boolean) {
    if (addOrRemove) {
        val updatedList = this.systems.toMutableList()
        updatedList.add(systems)
        this.systems = updatedList.toList()
    } else {
        val updatedList = this.systems.toMutableList()
        updatedList.remove(systems)
        this.systems = updatedList.toList()
    }
}