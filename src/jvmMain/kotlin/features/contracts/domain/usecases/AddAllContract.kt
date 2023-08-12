package features.contracts.domain.usecases

import utils.Resource
import features.contracts.domain.model.Contract
import features.contracts.domain.repository.ContractRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class AddAllContract(
    private val contractRepository: ContractRepository
){
    operator fun invoke(contracts: List<Contract>): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        println("AddAllContractUseCase is running")
        val result=contractRepository.addAllContract(contracts).first()
        println("AddALlContractUseCase is after getting data $result")
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("Cloud Not add all new contract")) }
}