package features.contracts.domain.usecases

import features.contracts.domain.model.Contract
import utils.Resource
import features.contracts.domain.repository.ContractXlsxRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class PrintContractsListToXlsxFile(
    private val contractXlsxRepository: ContractXlsxRepository
) {

    operator fun invoke(contracts: List<Contract>, filePath:String, headers:List<String>): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        println("persons have data $contracts")
        val result=contractXlsxRepository.printContractsToXlsxFile(contracts,filePath,headers)
        println("print to xlsx state  "+result.first())
        emit(Resource.Success(result.first()))
    }.catch { emit(Resource.Error("Cloud Not print to xlsx file")) }
}