package utils

import authorization.domain.model.Jobs
import authorization.domain.model.Jobs.*
import authorization.domain.model.Systems
import authorization.domain.model.User
import org.koin.core.context.GlobalContext.get

class UserAuthSystem{
    lateinit var currentUser:User


    fun logout() {
        currentUser = User(id = "", name = "", password = "", job = Jobs.None, systems = listOf())
    }
    fun canAccessScreen(screen:Systems):Boolean =
        currentUser.systems.contains(screen)
    fun canEdit(): Boolean = setOf(SuperAdmin,Admin).contains(currentUser.job)
    fun canChangeAccountsPermission():Boolean = currentUser.job == SuperAdmin
}


fun getUserAuth():UserAuthSystem = get().get<UserAuthSystem>()