package authorization.domain.repository

import kotlinx.coroutines.flow.Flow
import authorization.domain.model.User

interface AuthenticationRepository {


    fun getAllUsers():Flow<List<User>>
    suspend fun add(user: User): User
     fun isUser(user: User): Flow<User?>
     fun isThereUserWithName(username:String):Boolean
     fun getUser(user: User):Flow<User?>
    suspend fun updatePassword(userUpdate: User, newPassword:String)
    suspend fun updateUsername(userUpdate: User, newName:String)
    suspend fun updateUser(userUpdate: User):Flow<Boolean>

    suspend fun deleteUser(deletedUser:User):Flow<Boolean>
}