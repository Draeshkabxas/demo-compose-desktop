package authorization.data.dto

import authorization.data.model.UsersRealm
import authorization.domain.model.User
import io.realm.kotlin.ext.toRealmList
import org.mongodb.kbson.ObjectId

class UserToUserRealmDto {
    fun convert(user: User): UsersRealm {
        println("Convert User to UserRealm $user")
        return UsersRealm().apply {
            id = if (user.id.isEmpty()) ObjectId() else ObjectId(user.id)
            name = user.name
            password = user.password
            job = user.job.name
            systems = user.systems.map { it.name }.toRealmList()
        }
    }
}
