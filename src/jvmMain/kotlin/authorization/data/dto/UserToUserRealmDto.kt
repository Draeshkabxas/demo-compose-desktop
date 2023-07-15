package authorization.data.dto

import authorization.data.model.UserRealm
import authorization.domain.model.User
import org.mongodb.kbson.ObjectId

class UserToUserRealmDto {
    fun convert(user: User): UserRealm {
        return UserRealm().apply {
            id = if (user.id.isEmpty()) ObjectId() else ObjectId(user.id)
            name = user.name
            password = user.password
            job = user.job.name
            systems = user.systems.joinToString(",") { it.name }

        }
    }
}
