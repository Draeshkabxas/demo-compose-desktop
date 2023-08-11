package features.contracts.domain.usecases

import features.contracts.domain.model.Contract
import utils.Resource
import features.contracts.domain.repository.ContractRepository
import features.contracts.presentation.contracts.FilterState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class GetAllContracts(
    private val contractRepository: ContractRepository
) {
    operator fun invoke(filters: FilterState): Flow<Resource<List<Contract>>> = flow{
        emit(Resource.Loading(data = emptyList()))
        println(filters)
        val result=contractRepository.getAllContracts("")
        val resultAfterFiltered = result.first().filter {
            println(it)
            it.libyaId.contains(filters.libyaId) &&
                    it.fileNumber.contains(filters.fileNumber) &&
                    it.educationLevel.contains(filters.educationLevel) &&
                    it.city.contains(filters.city)&&
                    it.name.contains(filters.personName)&&
                    it.motherName.contains(filters.motherName)&&
                    it.libyaId.contains(filters.libyaId)&&
                    if (filters.ageGroup != null) it.ageGroup == filters.ageGroup else true
        }
        emit(Resource.Success(resultAfterFiltered))
    }.catch { emit(Resource.Error("Cloud Not add new person")) }
}
