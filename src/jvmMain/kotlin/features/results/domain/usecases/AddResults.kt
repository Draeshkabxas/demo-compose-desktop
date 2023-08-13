package features.results.domain.usecases

import features.results.domain.model.Results
import utils.Resource

import features.results.domain.repository.ResultsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class AddResults(
    private val resultsRepository: ResultsRepository
){
    operator fun invoke(results: Results): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        println("AddResultsUseCase is running")
        val result=resultsRepository.addResults(results).first()
        println("AddContractnUseCase is after getting data $result")
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("Cloud Not add new contract")) }
}