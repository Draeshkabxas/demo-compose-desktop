package authorization.data.repository

import authorization.data.dto.UserRealmToUserDto
import authorization.data.dto.UserToUserRealmDto
import authorization.data.model.UserRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import authorization.domain.model.User
import authorization.domain.repository.AppCloseRepository
import authorization.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.flow

class MangodbAuthenticationImpl(private val realm: Realm, private val app: AppCloseRepository) :
    AuthenticationRepository {
    init {
        app.addToCloseList {
            realm.close()
        }
    }

    override fun getAllUsers(): Flow<List<User>> {
        return realm.query<UserRealm>().asFlow()
            .map {
                it.list.map { userRealm -> UserRealmToUserDto().convert(userRealm) }
            }
    }

    override suspend fun add(user: User): User {
        realm.writeBlocking {
            try {
                copyToRealm(UserToUserRealmDto().convert(user))
            } catch (e: Exception) {
                return@writeBlocking false
            }
        }
        return user
    }

    override fun getUser(user: User): Flow<User?> = flow {
        val data = realm.query<UserRealm>("name == $0 && password == $1", user.name, user.password).first().find()
        if (data == null) {
            emit(null)
        } else {
            emit(UserRealmToUserDto().convert(data))
        }
    }

    override fun isUser(userRealm: User): Flow<Boolean> {
        return getUser(userRealm).map { it != null }
    }

    override fun isThereUserWithName(username: String): Boolean {
        val usersCount = realm.query<UserRealm>("name == $0", username).count()
        return usersCount.find() > 0
    }

    override suspend fun updatePassword(userUpdate: User, newPassword: String) {
        val user = getUser(userUpdate).first() ?: return
        realm.writeBlocking {
            UserToUserRealmDto().convert(user).let {
                findLatest(it)?.password = newPassword
            }
        }
    }

    override suspend fun updateUsername(userUpdate: User, newName: String) {
        val user = getUser(userUpdate).first() ?: return
        realm.writeBlocking {
            UserToUserRealmDto().convert(user).let {
                findLatest(it)?.name = newName
            }
        }
    }
}