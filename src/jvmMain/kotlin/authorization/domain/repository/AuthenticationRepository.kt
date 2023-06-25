package authorization.domain.repository

import kotlinx.coroutines.flow.Flow
import authorization.data.model.User

interface AuthenticationRepository {


    fun getAllUsers():Flow<List<User>>
    suspend fun add(user: User)
     fun isUser(user: User): Flow<Boolean>
     fun getUser(user: User):Flow<User?>
    suspend fun updatePassword(userUpdate: User, newPassword:String)
    suspend fun updateUsername(userUpdate: User, newName:String)
}