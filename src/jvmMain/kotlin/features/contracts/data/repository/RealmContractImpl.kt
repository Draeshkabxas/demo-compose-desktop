package features.contracts.data.repository

import features.contracts.data.dto.toContractDto
import features.contracts.data.dto.toRealmContractDto
import features.contracts.data.model.RealmContract
import features.contracts.domain.model.Contract
import features.contracts.domain.repository.ContractRepository
import features.courses.data.dto.toCourseDTO
import features.courses.data.dto.toRealmCourse
import features.courses.data.model.RealmCourse
import features.courses.domain.model.Course
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RealmContractImpl(private val realm: Realm) :
    ContractRepository {

    override fun getAllContracts(filterQuery: String): Flow<List<Contract>>  {
        return realm.query<RealmContract>()
            .asFlow()
            .map {
                it.list.map { realmContract -> realmContract.toContractDto() }.reversed()
            }
    }

    override fun addContract(contract: Contract): Flow<Boolean> = flow{
        var result = true
        realm.writeBlocking {
            try {
                copyToRealm(contract.toRealmContractDto())
            } catch (e: Exception) {
                println(e.localizedMessage)
                result = false
            }
        }
        emit(result)
    }



    override fun getContract(id: String): Flow<Contract?> = flow{
        val data = realm.query<RealmContract>("id == $0",id).first().find()
        if (data == null) {
            emit(null)
        } else {
            emit(data.toContractDto())
        }
    }
    private fun getContract(contract: Contract): RealmContract? {
      return  realm.query<RealmContract>("id = $0", contract.id)
            .first()
            .find()
    }

    override fun updateContract(contract: Contract): Flow<Boolean> = flow{
        var result = false
        try {
            realm.writeBlocking {
                val updatedContract = contract.toRealmContractDto()
                query<RealmContract>("id = $0", contract.id)
                    .first()
                    .find()
                    ?.also { oldContract ->
                        findLatest(oldContract.apply {
                            name = updatedContract.name
                            motherName = updatedContract.motherName
                            motherNationality = updatedContract.motherNationality
                            fileNumber = updatedContract.fileNumber
                            libyaId = updatedContract.libyaId
                            phoneNumber = updatedContract.phoneNumber
                            dependency = updatedContract.dependency
                            bankName = updatedContract.bankName
                            ageGroup = updatedContract.ageGroup
                            accountNumber = updatedContract.accountNumber
                            educationLevel = updatedContract.educationLevel
                            city = updatedContract.city
                            notes = updatedContract.notes
                        })
                    }
                result = true
            }
        } catch (e: Exception) {
            println("Realm update course impl has error ${e.localizedMessage}")
        }
        emit(result)
    }
    override fun removeAllContract(): Flow<Boolean> = flow {
        var result = true
        realm.writeBlocking {
            try {
                delete(RealmContract::class)
            }catch (e:Exception){
                result = false
            }
        }
        emit(result)
    }

    override fun removeContract(contract: Contract): Flow<Boolean> = flow {
        var result = false
        try {
            realm.writeBlocking {
                getContract(contract)?.also {
                    findLatest(it)?.also {removedContract->
                        delete(removedContract)
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