package features.contracts.domain.usecases

import features.contracts.domain.model.Contract
import utils.Resource
import features.contracts.domain.repository.ContractRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class RemoveContract(
    private val contractRepository: ContractRepository
) {
    operator fun invoke(removedContract: Contract): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        println("remove Course is running $removedContract")
        val result=contractRepository.removeContract(removedContract).first()
        println("remove Course after getting data $result")
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("Cloud Not remove this Course")) }
}