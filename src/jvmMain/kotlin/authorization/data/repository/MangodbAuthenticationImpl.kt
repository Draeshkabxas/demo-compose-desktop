package authorization.data.repository

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import authorization.data.model.User
import authorization.domain.repository.AppCloseRepository
import authorization.domain.repository.AuthenticationRepository

class MangodbAuthenticationImpl(private val realm:Realm,private val app: AppCloseRepository): AuthenticationRepository {
    init {
        println("Im worked")
        app.addToCloseList {
            realm.close()
        }
    }
    override fun getAllUsers(): Flow<List<User>> {
        return realm.query<User>().asFlow().map { it.list }
    }

    override suspend fun add(user: User) {
         realm.writeBlocking {
            try {
                copyToRealm(user)
            }catch (e:Exception){
                return@writeBlocking false
            }
        }
    }

    override fun getUser(user: User): Flow<User?> {
        return realm.query<User>().asFlow().map { it.list.find { queryUser->
            (user.name == queryUser.name) && user.password == queryUser.password }
        }
    }

    override fun isUser(user: User): Flow<Boolean> {
        return getUser(user).map { it != null}
    }

    override suspend fun updatePassword(userUpdate: User, newPassword:String) {
        val user = getUser(userUpdate).first() ?: return
        realm.writeBlocking {
            user.let {
                findLatest(it)?.password = newPassword
            }
        }
    }

    override suspend fun updateUsername(userUpdate: User, newName: String) {
        val user = getUser(userUpdate).first() ?: return
        realm.writeBlocking {
            user.let {
                findLatest(it)?.name=newName
            }
        }
    }
}