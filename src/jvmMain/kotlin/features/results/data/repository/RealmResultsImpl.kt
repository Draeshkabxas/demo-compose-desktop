package features.results.data.repository


import features.results.data.dto.toRealmResultsDto
import features.results.data.dto.toResultsDto

import features.results.data.model.RealmResults
import features.results.domain.model.Results
import features.results.domain.repository.ResultsRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RealmResultsImpl(private val realm: Realm) :
    ResultsRepository {

    override fun getAllResults(filterQuery: String): Flow<List<Results>>  {
        return realm.query<RealmResults>()
            .asFlow()
            .map {
                it.list.map { realmResults -> realmResults.toResultsDto() }.reversed()
            }
    }

    override fun addResults(results: Results): Flow<Boolean> = flow{
        var result = true
        realm.writeBlocking {
            try {
                copyToRealm(results.toRealmResultsDto())
            } catch (e: Exception) {
                println(e.localizedMessage)
                result = false
            }
        }
        emit(result)
    }

    override fun addAllResults(results: List<Results>): Flow<Boolean> = flow{
           var result = true
        realm.writeBlocking {
            try {
                results.forEach {results->
                    copyToRealm(results.toRealmResultsDto())
                }
            } catch (e: Exception) {
                println(e.localizedMessage)
                result = false
            }
        }
        emit(result)
    }


    override fun getResults(id: String): Flow<Results?> = flow{
        val data = realm.query<RealmResults>("id == $0",id).first().find()
        if (data == null) {
            emit(null)
        } else {
            emit(data.toResultsDto())
        }
    }
    private fun getResult(results: Results): RealmResults? {
      return  realm.query<RealmResults>("id = $0", results.id)
            .first()
            .find()
    }

    override fun updateResults(results: Results): Flow<Boolean> = flow{
        var resultss = false
        try {
            realm.writeBlocking {
                val updatedResults = results.toRealmResultsDto()
                query<RealmResults>("id = $0", results.id)
                    .first()
                    .find()
                    ?.also { oldResults ->
                        findLatest(oldResults.apply {
                            name = updatedResults.name
//                            motherName = updatedContract.motherName
//                            motherNationality = updatedContract.motherNationality
//                            fileNumber = updatedContract.fileNumber
//                            libyaId = updatedContract.libyaId
                            phoneNumber = updatedResults.phoneNumber
                            result = updatedResults.result
//                            bankName = updatedContract.bankName
//                            ageGroup = updatedContract.ageGroup
//                            accountNumber = updatedContract.accountNumber
//                            educationLevel = updatedContract.educationLevel
                            date = updatedResults.date
                            notes = updatedResults.notes
                        })
                    }
                resultss = true
            }
        } catch (e: Exception) {
            println("Realm update Results impl has error ${e.localizedMessage}")
        }
        emit(resultss)
    }
    override fun removeAllResults(): Flow<Boolean> = flow {
        var result = true
        realm.writeBlocking {
            try {
                delete(RealmResults::class)
            }catch (e:Exception){
                result = false
            }
        }
        emit(result)
    }

    override fun removeResults(results: Results): Flow<Boolean> = flow {
        var result = false
        try {
            realm.writeBlocking {
                getResult(results)?.also {
                    findLatest(it)?.also {removedResults->
                        delete(removedResults)
                    }
                }
                result = true
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        emit(result)
    }
}