package features.contracts.data.repository

import features.contracts.data.dto.toContractDto
import features.contracts.data.dto.toRealmContractDto
import features.contracts.data.model.RealmContract
import features.contracts.domain.model.Contract
import features.contracts.domain.repository.ContractRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RealmPersonImpl(private val realm: Realm) :
    ContractRepository {
    init {
        Runtime.getRuntime().addShutdownHook(Thread {
            realm.close()
        })
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

    override fun getAllContracts(): Flow<List<Contract>> {
        return realm.query<RealmContract>().asFlow()
            .map {
                it.list.map { realmContract -> realmContract.toContractDto() }
            }
    }

    override fun getContract(id: String): Flow<Contract?> = flow{
        val data = realm.query<RealmContract>("id == $0",id).first().find()
        if (data == null) {
            emit(null)
        } else {
            emit(data.toContractDto())
        }
    }

    override fun updateContract(contract: Contract): Flow<Boolean> = flow{
        val oldContract:Contract? = getContract(contract.id).first()
        val updatedContract = contract.toRealmContractDto()
        var result = false
        if (oldContract == null) emit(false)
        realm.writeBlocking {
            oldContract?.let {
                findLatest(it.toRealmContractDto())?.apply {
                   name = updatedContract.name
                   motherName = updatedContract.motherName
                   motherNationality = updatedContract.motherNationality
                   fileNumber = updatedContract.fileNumber
                   libyaId = updatedContract.libyaId
                   phoneNumber = updatedContract.phoneNumber
                   dependency = updatedContract.dependency
                   bankName = updatedContract.bankName
                   accountNumber = updatedContract.accountNumber
                   educationLevel = updatedContract.educationLevel
                   city = updatedContract.city
                   notes = updatedContract.notes
                }
            }
            result = true
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
}