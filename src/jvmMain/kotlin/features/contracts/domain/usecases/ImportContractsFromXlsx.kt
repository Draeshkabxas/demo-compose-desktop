package features.contracts.domain.usecases

import utils.Resource
import features.contracts.domain.model.Contract
import features.contracts.domain.repository.ContractXlsxRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import utils.filePrintLn

class ImportContractsFromXlsx(
    private val contractXlsxRepository: ContractXlsxRepository
) {
    operator fun invoke(filePath:String): Flow<Resource<List<Contract>>> = flow{
        emit(Resource.Loading(data = emptyList()))
        filePrintLn("")
        val result=contractXlsxRepository.getContractsFromXlsxFile(filePath).first()
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("${it.message} $it  Cloud Not import contracts from this $filePath xlsx file")) }
}