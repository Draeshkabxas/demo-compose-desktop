package authorization.presentation.accountsPermissions

import authorization.domain.model.User
import authorization.domain.usecase.DeleteUser
import authorization.domain.usecase.GetAllUsers
import authorization.domain.usecase.UpdateUser
import utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

class AccountPermissionViewModel(
    private val updateUser: UpdateUser,
    private val getAllUsers: GetAllUsers,
    private val deleteUserUseCase: DeleteUser
) {
    private val usersDataChannel = Channel<List<User>>()

    private var usersData:List<User> = emptyList()
    val usersDataFlow = usersDataChannel.receiveAsFlow()

    init {
        getAllUsers()
    }

    fun deleteUser(user:User){
        deleteUserUseCase.invoke(user).onEach {
            when (it) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    println("the user ${user.name} deleted successfully")
                }
            }
            refreshUsersData()
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

     fun updateUser(user:User){
        updateUser.invoke(user).onEach {
            when (it) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    println("the user ${user.name} updated successfully")
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }


    private fun getAllUsers() {
        try {
            getAllUsers.invoke().onEach {
                when (it) {
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        it.data?.let { users ->
                            usersData = users
                            usersDataChannel.send(users)
                            println(users)
                        }
                    }
                }
            }.launchIn(CoroutineScope(Dispatchers.IO))
        } catch (e: Exception) {
            println(e.localizedMessage)
        }

    }

    fun refreshUsersData() {
        getAllUsers()
    }
}