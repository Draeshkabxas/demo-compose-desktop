package authorization.domain.usecase
import authorization.domain.model.User
import authorization.domain.repository.AuthenticationRepository
import utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class GetUser(
    private val authRepo:AuthenticationRepository
){ operator fun invoke(user: User): Flow<Resource<out User?>> = flow{
    emit(Resource.Loading(data = null))
    val result= authRepo.getUser(user)
    val resultAfterFiltered = result.first()
    emit(Resource.Success(resultAfterFiltered))
}.catch { emit(Resource.Error("Could not get user")) }}