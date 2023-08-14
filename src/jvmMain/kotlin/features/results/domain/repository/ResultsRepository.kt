package features.results.domain.repository

import features.results.domain.model.Results
import kotlinx.coroutines.flow.Flow

interface ResultsRepository {
    fun addResults(results: Results): Flow<Boolean>
    fun addAllResults(results: List<Results>): Flow<Boolean>
    fun getAllResults(filterQuery:String):Flow<List<Results>>
    fun getResults(id: String): Flow<Results?>
    fun updateResults(results: Results): Flow<Boolean>
    fun removeAllResults(): Flow<Boolean>

    fun removeResults(results: Results):Flow<Boolean>
}