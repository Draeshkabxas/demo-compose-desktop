package features.results.domain.usecases

import utils.Resource
import features.contracts.domain.model.Contract
import features.contracts.domain.repository.ContractXlsxRepository
import features.results.domain.model.Results
import features.results.domain.repository.ResultsXlsxRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class ImportResultsFromXlsx(
    private val resultsXlsxRepository: ResultsXlsxRepository
) {

    operator fun invoke(filePath:String): Flow<Resource<List<Results>>> = flow{
        emit(Resource.Loading(data = emptyList()))
        val result=resultsXlsxRepository.getResultsFromXlsxFile(filePath).first()
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("Cloud Not import results from this $filePath xlsx file")) }
}