package authorization.data.repository

import authorization.data.dto.UserRealmToUserDto
import authorization.data.dto.UserToUserRealmDto
import authorization.data.model.UsersRealm
import authorization.domain.model.Jobs.SuperAdmin
import authorization.domain.model.Systems.*
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import authorization.domain.model.User
import authorization.domain.model.addOrRemoveScreenFromSystem
import authorization.domain.repository.AppCloseRepository
import authorization.domain.repository.AuthenticationRepository
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.flow.*

class MangodbAuthenticationImpl(private val realm: Realm, private val app: AppCloseRepository) :
    AuthenticationRepository {

    override fun getAllUsers(): Flow<List<User>> {
        return realm.query<UsersRealm>().asFlow()
            .map {
                it.list.map { userRealm -> UserRealmToUserDto().convert(userRealm) }
            }
    }

    override suspend fun add(user: User): User {
        if (getAllUsers().first().isEmpty()){
            user.job = SuperAdmin
            user.addOrRemoveScreenFromSystem(Results,true)
            user.addOrRemoveScreenFromSystem(SonsOfOfficers,true)
            user.addOrRemoveScreenFromSystem(Contracts,true)
            user.addOrRemoveScreenFromSystem(Courses,true)
            user.addOrRemoveScreenFromSystem(Home,true)
        }
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
        val data = realm.query<UsersRealm>("name == $0 && password == $1", user.name, user.password).first().find()
        if (data == null) {
            emit(null)
        } else {
            emit(UserRealmToUserDto().convert(data))
        }
    }

    override fun isUser(user: User): Flow<User?> {
        return getUser(user)
    }

    override fun isThereUserWithName(username: String): Boolean {
        val usersCount = realm.query<UsersRealm>("name == $0", username).count()
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

    override suspend fun updateUser(userUpdate: User):Flow<Boolean> {
        var result = true
        try {
            realm.writeBlocking {
                query<UsersRealm>(" name == $0 && password == $1",userUpdate.name,userUpdate.password)
                    .first()
                    .find()?.also {
                        findLatest(it)?.apply {
                            name = userUpdate.name
                            job= userUpdate.job.toString()
                            systems = userUpdate.systems.map {system-> system.name }.toRealmList()
                        }
                    }
                query<UsersRealm>(" name == $0 && password == $1",userUpdate.name,userUpdate.password)
                    .first()
                    .find()?.also {
                        println("inside the update user impl $it")
                    }
            }
        }catch (e:Exception){
            result = false
        }
        return flowOf(result)
    }

    override suspend fun deleteUser(deletedUser: User): Flow<Boolean> {
        var result = true
        try {
            realm.writeBlocking {
                query<UsersRealm>(" name == $0 && password == $1",deletedUser.name,deletedUser.password)
                    .first()
                    .find()?.also {
                        delete(it)
                    }
            }
        }catch (e:Exception){
            result = false
        }
        return flowOf(result)    }

    override fun logout() {
        TODO("Not yet implemented")
    }


}