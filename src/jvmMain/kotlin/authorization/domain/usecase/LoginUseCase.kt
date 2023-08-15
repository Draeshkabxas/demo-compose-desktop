package authorization.domain.usecase

import authorization.domain.model.Jobs
import authorization.domain.model.Jobs.None
import authorization.domain.model.User
import authorization.domain.repository.AuthenticationRepository
import utils.Resource
import kotlinx.coroutines.flow.*

class LoginUseCase(private val auth: AuthenticationRepository) {
    operator fun invoke(user: User): Flow<Resource<out User?>> = flow {
        emit(Resource.Loading(null))
        println("login state is  loading..")
        val result = auth.isUser(user).first()
        println("login state is  $result")
        if (result == null) {
            emit(Resource.Error(message = "اسم المستخدم أو كلمة المرور خاطئة"))
        } else if (result.job == None) {
            emit(Resource.Error(message = " يرجى التواصل مع مديرك لإعطائك صلاحية الدخول للنظام"))
        }else {
            emit(Resource.Success(result))
        }
    }.catch { emit(Resource.Error("Cloud Not login")) }
}