package features.contracts.domain.usecases

import features.contracts.domain.model.Contract


class ChangeAllContractsEducationLevel() {
    operator fun invoke(contractsGroupedByEducationLevel: Map<String,List<Contract>>,convertMap:Map<String,String>): List<Contract> {
        convertMap.forEach {
            println("${it.key} :: ${it.value}")
        }
        var contracts = contractsGroupedByEducationLevel
        convertMap.forEach { (from, to) ->
            contracts = contracts.changeContractsEducationLevel(from,to)
        }
        val contractsResultList = mutableListOf<Contract>()
        contracts.values . forEach {
            contractsResultList.addAll(it)
        }
        contractsResultList.take(10).forEach {
            println("${it.name} :: ${it.educationLevel}")
        }

        return  contractsResultList
    }
}

fun Map<String,List<Contract>>.changeContractsEducationLevel(fromEducationLevel :String, toEducationLevel :String): Map<String, List<Contract>> {
    val contractsGroupedByEducationLevel = this.toMutableMap()

    val contractsList = this[fromEducationLevel]?.toList() ?: emptyList()
    if (contractsGroupedByEducationLevel.containsKey(toEducationLevel)){
        val addedList = contractsGroupedByEducationLevel[toEducationLevel]?.toMutableList() ?: emptyList<Contract>().toMutableList()
        addedList.addAll(contractsList)
        contractsGroupedByEducationLevel[toEducationLevel] = addedList.toList()
    }else{
        contractsGroupedByEducationLevel[toEducationLevel] = contractsList
    }
    val mutableContracts = contractsGroupedByEducationLevel[toEducationLevel]?.toMutableList() ?: mutableListOf()
    contractsGroupedByEducationLevel[toEducationLevel]?.forEachIndexed {index,contreact->
        mutableContracts[index] = contreact.copy(educationLevel = toEducationLevel)
    }
    contractsGroupedByEducationLevel[toEducationLevel] = mutableContracts.toList()
    contractsGroupedByEducationLevel.remove(fromEducationLevel)
    return contractsGroupedByEducationLevel.toMap()
}
