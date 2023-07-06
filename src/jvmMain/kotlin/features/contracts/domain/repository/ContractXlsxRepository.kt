package features.contracts.domain.repository

import features.sons_of_officers.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface ContractXlsxRepository {
    fun printPersonsToXlsxFile(persons:List<Person>, filePath:String): Flow<Boolean>

    fun getPersonsFromXlsxFile(path:String):Flow<List<Person>>
}