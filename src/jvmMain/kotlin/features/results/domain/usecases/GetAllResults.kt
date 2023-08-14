package features.results.domain.usecases

import utils.Resource

import features.results.domain.model.Results
import features.results.domain.repository.ResultsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class GetAllResults(
    private val resultsRepository: ResultsRepository
) {
    operator fun invoke(filters: features.results.presentation.results.FilterState): Flow<Resource<List<Results>>> = flow{
        emit(Resource.Loading(data = emptyList()))
        val result=resultsRepository.getAllResults("")
        val resultAfterFiltered = result.first().filter {
                    it.date.contains(filters.date)&&
                    it.name.contains(filters.personName)
        }
        emit(Resource.Success(resultAfterFiltered))
    }.catch { emit(Resource.Error("Cloud Not add new person")) }
}
