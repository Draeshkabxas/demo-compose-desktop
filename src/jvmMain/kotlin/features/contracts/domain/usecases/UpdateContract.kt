package features.contracts.domain.usecases

import utils.Resource
import features.contracts.domain.model.Contract
import features.contracts.domain.repository.ContractRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class UpdateContract(
    private val contractRepository: ContractRepository
) {
    operator fun invoke(updatedContract: Contract): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        println("Update Course is running $updatedContract")
        val result=contractRepository.updateContract(updatedContract).first()
        println("Update Course after getting data $result")
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("Cloud Not update this Course")) }
}