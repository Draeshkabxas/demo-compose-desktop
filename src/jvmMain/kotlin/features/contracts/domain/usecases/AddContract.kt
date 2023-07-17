package features.contracts.domain.usecases

import utils.Resource
import features.contracts.domain.model.Contract
import features.contracts.domain.repository.ContractRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class AddContract(
    private val contractRepository: ContractRepository
){
    operator fun invoke(contract: Contract): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        println("AddContractUseCase is running")
        val result=contractRepository.addContract(contract).first()
        println("AddContractnUseCase is after getting data $result")
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("Cloud Not add new contract")) }
}