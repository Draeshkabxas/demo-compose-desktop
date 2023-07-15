package features.sons_of_officers.domain.usecases

import common.Resource
import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.domain.repository.PersonXlsxRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class PrintPersonsListToXlsxFile(
    private val personXlsxRepository: PersonXlsxRepository
) {

    operator fun invoke(persons: List<Person>, filePath:String,headers:List<String>): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(data = true))
        println("persons have data $persons")
        val result=personXlsxRepository.printPersonsToXlsxFile(persons,filePath,headers)
        println("print to xlsx state  "+result.first())
        emit(Resource.Success(result.first()))
    }.catch { emit(Resource.Error("Cloud Not print to xlsx file")) }
}