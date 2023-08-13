package authorization.data.dto

import authorization.data.model.UsersRealm
import authorization.domain.model.Jobs
import authorization.domain.model.Systems
import authorization.domain.model.User


class UserRealmToUserDto {
    fun convert(userRealm: UsersRealm): User {
        println("Convert UserRealm to User $userRealm")
        return User(
            id = userRealm.id.toHexString(),
            name = userRealm.name,
            password = userRealm.password,
            job = Jobs.valueOf(userRealm.job),
            systems = userRealm.systems.map { Systems.valueOf(it) }
        )
    }
}
