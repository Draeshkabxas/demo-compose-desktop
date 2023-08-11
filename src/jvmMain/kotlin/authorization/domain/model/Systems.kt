package authorization.domain.model

enum class Systems {
    Home,
    Contracts,
    Courses,
    SonsOfOfficers,
    Results;
}

fun List<Systems>.joinToString(delimiter: String = ","): String {
    return this.joinToString(delimiter) { it.name }
}