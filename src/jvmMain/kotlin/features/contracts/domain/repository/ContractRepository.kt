package features.contracts.domain.repository

import features.contracts.domain.model.Contract
import features.courses.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface ContractRepository {
    fun addContract(contract: Contract): Flow<Boolean>
    fun getAllContracts(filterQuery:String):Flow<List<Contract>>
    fun getContract(id: String): Flow<Contract?>
    fun updateContract(contract: Contract): Flow<Boolean>
    fun removeAllContract(): Flow<Boolean>

    fun removeContract(contract: Contract):Flow<Boolean>
}