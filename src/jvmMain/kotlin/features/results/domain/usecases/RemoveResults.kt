package features.results.domain.usecases

import utils.Resource

import features.results.domain.model.Results
import features.results.domain.repository.ResultsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class RemoveResults(
    private val resultsRepository: ResultsRepository
) {
    operator fun invoke(removedResults: Results): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        println("remove Results is running $removedResults")
        val result=resultsRepository.removeResults(removedResults).first()
        println("remove Results after getting data $result")
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("Cloud Not remove this Results")) }
}