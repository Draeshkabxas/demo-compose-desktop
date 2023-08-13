package features.results.domain.usecases

import utils.Resource
import features.results.domain.repository.ResultsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class RemoveAllResults(
    private val resultsRepository: ResultsRepository
){
    operator fun invoke(): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        val result=resultsRepository.removeAllResults().first()
        emit(Resource.Success(result))
    }.catch { emit(Resource.Error("Cloud Not remove all Results")) }
}