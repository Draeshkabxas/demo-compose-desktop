package features.results.domain.usecases

import utils.Resource

import features.results.domain.model.Results
import features.results.domain.repository.ResultsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class UpdateResults(
    private val resultsRepository: ResultsRepository
) {
    operator fun invoke(updatedResults: Results): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        println("Update Results is running $updatedResults")
        val result=resultsRepository.updateResults(updatedResults).first()
        println("Update Course after getting data $result")
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("Cloud Not update this Course")) }
}