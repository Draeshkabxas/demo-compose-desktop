package features.sons_of_officers.domain.repository

import features.sons_of_officers.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface PersonXlsxRepository {
    fun printPersonsToXlsxFile(persons:List<Person>, filePath:String,headers:List<String>): Flow<Boolean>

    fun getPersonsFromXlsxFile(path:String):Flow<List<Person>>
}