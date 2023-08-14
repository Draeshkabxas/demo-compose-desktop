package features.results.domain.usecases

import utils.Resource
import features.contracts.domain.model.Contract
import features.results.domain.model.Results
import features.results.domain.repository.ResultsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class AddAllResults(
    private val resultsRepository: ResultsRepository
){
    operator fun invoke(results: List<Results>): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        println("AddAllResults is running")
        val result=resultsRepository.addAllResults(results).first()
        println("AddAllResults is after getting data $result")
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("Cloud Not add all new results")) }
}