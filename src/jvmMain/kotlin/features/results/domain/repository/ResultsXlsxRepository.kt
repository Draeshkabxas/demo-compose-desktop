package features.results.domain.repository

import features.results.domain.model.Results
import kotlinx.coroutines.flow.Flow

interface ResultsXlsxRepository {
    fun printResultsToXlsxFile(results:List<Results>, filePath:String, headers:List<String>): Flow<Boolean>

    fun getResultsFromXlsxFile(path:String):Flow<List<Results>>
}