package authorization.data.dto

import authorization.data.model.UserRealm
import authorization.domain.model.Jobs
import authorization.domain.model.Systems
import authorization.domain.model.User

class UserRealmToUserDto {
    fun convert(userRealm: UserRealm): User {
        return User(
            id = userRealm.id.toHexString(),
            name = userRealm.name,
            password = userRealm.password,
            job = Jobs.valueOf(userRealm.job),
            systems = userRealm.systems.mapNotNull { system ->
                try {
                    Systems.valueOf(system.toString())
                } catch (e: IllegalArgumentException) {
                    null
                }
            }
        )
    }
}
