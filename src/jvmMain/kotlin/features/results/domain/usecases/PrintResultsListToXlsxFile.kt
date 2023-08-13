package features.results.domain.usecases

import utils.Resource
import features.results.domain.model.Results
import features.results.domain.repository.ResultsXlsxRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class PrintResultsListToXlsxFile(
    private val resultsXlsxRepository: ResultsXlsxRepository
) {

    operator fun invoke(results: List<Results>, filePath:String, headers:List<String>): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        println("persons have data $results")
        val result=resultsXlsxRepository.printResultsToXlsxFile(results,filePath,headers)
        println("print to xlsx state  "+result.first())
        emit(Resource.Success(result.first()))
    }.catch { emit(Resource.Error("Cloud Not print to xlsx file")) }
}