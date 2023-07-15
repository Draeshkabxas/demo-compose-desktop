package features.contracts.domain.repository

import features.contracts.domain.model.Contract
import kotlinx.coroutines.flow.Flow

interface ContractXlsxRepository {
    fun printContractsToXlsxFile(contracts:List<Contract>, filePath:String, headers:List<String>): Flow<Boolean>

    fun getContractsFromXlsxFile(path:String):Flow<List<Contract>>
}