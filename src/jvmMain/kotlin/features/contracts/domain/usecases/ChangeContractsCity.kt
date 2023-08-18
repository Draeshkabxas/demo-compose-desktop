package features.contracts.domain.usecases

import features.contracts.domain.model.Contract


class ChangeAllContractsCity() {
    operator fun invoke(contractsGroupedByCity: Map<String,List<Contract>>,convertMap:Map<String,String>): List<Contract> {
        convertMap.forEach {
            println("${it.key} :: ${it.value}")
        }
        var contracts = contractsGroupedByCity
        convertMap.forEach { (from, to) ->
            contracts = contracts.changeContractsCity(from,to)
        }
        val contractsResultList = mutableListOf<Contract>()
        contracts.values . forEach {
            contractsResultList.addAll(it)
        }
        contractsResultList.take(10).forEach {
            println("${it.name} :: ${it.city}")
        }

        return  contractsResultList
    }
}

private fun Map<String,List<Contract>>.changeContractsCity(fromCity :String, toCity :String): Map<String, List<Contract>> {
    val contractsGroupedByCity = this.toMutableMap()
    if (fromCity  == toCity){
        return contractsGroupedByCity.toMap()
    }
    val contractsList = this[fromCity]?.toList() ?: emptyList()
    if (contractsGroupedByCity.containsKey(toCity)){
        val addedList = contractsGroupedByCity[toCity]?.toMutableList() ?: emptyList<Contract>().toMutableList()
        addedList.addAll(contractsList)
        contractsGroupedByCity[toCity] = addedList.toList()
    }else{
        contractsGroupedByCity[toCity] = contractsList
    }
    val mutableContracts = contractsGroupedByCity[toCity]?.toMutableList() ?: mutableListOf()
    contractsGroupedByCity[toCity]?.forEachIndexed {index,contreact->
        mutableContracts[index] = contreact.copy(city = toCity)
    }
    contractsGroupedByCity[toCity] = mutableContracts.toList()
    contractsGroupedByCity.remove(fromCity)
    return contractsGroupedByCity.toMap()
}
