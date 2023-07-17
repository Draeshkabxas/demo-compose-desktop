package utils

import authorization.domain.model.Jobs.*
import authorization.domain.model.Systems
import authorization.domain.model.User

class UserAuthSystem{
    lateinit var currentUser:User
    fun canAccessScreen(screen:Systems):Boolean =
        currentUser.systems.contains(screen)
    fun canEdit(): Boolean = setOf(SuperAdmin,Admin).contains(currentUser.job)
    fun canChangeAccountsPermission():Boolean = currentUser.job == SuperAdmin
}