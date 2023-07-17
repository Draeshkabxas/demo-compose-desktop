package features.contracts.domain.usecases

import utils.Resource
import features.contracts.domain.repository.ContractRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class RemoveAllContracts(
    private val contractRepository: ContractRepository
){
    operator fun invoke(): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        val result=contractRepository.removeAllContract().first()
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("Cloud Not remove all contract")) }
}