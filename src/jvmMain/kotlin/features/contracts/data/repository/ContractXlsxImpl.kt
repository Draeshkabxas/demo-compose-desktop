package features.contracts.data.repository

import common.domain.xlsxToListOf
import features.contracts.domain.model.Contract
import features.contracts.domain.repository.ContractXlsxRepository
//import features.contracts.domain.model.result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import utils.AgeGroup
import utils.filePrintLn
import utils.getAgeGroupFromLibyaId
import java.io.FileOutputStream

class ContractXlsxImpl : ContractXlsxRepository {

    override fun printContractsToXlsxFile(
        contracts: List<Contract>,
        filePath: String,
        headers: List<String>
    ): Flow<Boolean> = flow {
        val workbook = XSSFWorkbook()
        try {
            val sheet = workbook.createSheet("Persons")
            val headerRow = sheet.createRow(0)
            headers.forEachIndexed { index: Int, headerName: String ->
                headerRow.createCell(index).setCellValue(headerName)
            }
            // ... create other header cells
            contracts.forEachIndexed { index, contract ->
                val row = sheet.createRow(index + 1)
                headers.forEachIndexed { cellIndex: Int, headerName: String ->
                    when (headerName.trim()) {
                        "رقم الملف" -> {
                            row.createCell(cellIndex).setCellValue(contract.fileNumber)
                        }

                        "الاسم رباعي" -> {
                            row.createCell(cellIndex).setCellValue(contract.name)
                        }

                        "اسم الام" -> {
                            row.createCell(cellIndex).setCellValue(contract.motherName)
                        }

                        "المؤهل العلمي" -> {
                            row.createCell(cellIndex).setCellValue(contract.educationLevel)
                        }

                        "المدينة" -> {
                            row.createCell(cellIndex).setCellValue(contract.city)
                        }

                        "رقم الهاتف" -> {
                            row.createCell(cellIndex).setCellValue(contract.phoneNumber)
                        }

                        "جنسية الام" -> {
                            row.createCell(cellIndex).setCellValue(contract.motherNationality)
                        }

                        "التبعية" -> {
                            row.createCell(cellIndex).setCellValue(contract.dependency)
                        }

                        "اسم المصرف" -> {
                            row.createCell(cellIndex).setCellValue(contract.bankName)
                        }

                        "رقم الحساب" -> {
                            row.createCell(cellIndex).setCellValue(contract.accountNumber)
                        }

                        "الرقم الوطني" -> {
                            row.createCell(cellIndex).setCellValue(contract.libyaId)
                        }

                        "الرقم الاشاري" -> {
                            row.createCell(cellIndex).setCellValue(contract.reference)
                        }
                        "الملاحظات" -> {
                            row.createCell(cellIndex).setCellValue(contract.notes)
                        }
                        else -> {}
                    }
                }
            }
            FileOutputStream("$filePath/العقود.xlsx").use { outputStream ->
                workbook.write(outputStream)
                workbook.close()
            }
            emit(true)
        } catch (e: Exception) {
            filePrintLn(e.localizedMessage)
            workbook.close()
            emit(false)
        }
    }.flowOn(Dispatchers.IO)


    override fun getContractsFromXlsxFile(filePath: String): Flow<List<Contract>> = flow {
        val map: Map<String, (Contract, String) -> Contract> = mapOf(
            "رقم الملف" to { contract: Contract, value: String -> contract.copy(fileNumber = value) },
            "الاسم رباعي" to { contract: Contract, value: String -> contract.copy(name = value) },
            "اسم الام" to { contract: Contract, value: String -> contract.copy(motherName = value) },
            "المدينة" to { contract: Contract, value: String -> contract.copy(city = value.filter { it.isLetter() }) },
            "جنسية الام" to { contract: Contract, value: String -> contract.copy(motherNationality = value) },
            "الرقم الوطني" to { contract: Contract, value: String ->
                contract.copy(
                    libyaId = value,
                    ageGroup = getAgeGroupFromLibyaId(value)
                )
            },
            "رقم الهاتف" to { contract: Contract, value: String -> contract.copy(phoneNumber = value) },
            "التبعية" to { contract: Contract, value: String -> contract.copy(dependency = value) },
            "الملاحظات" to { contract: Contract, value: String -> contract.copy(notes = value) },
            "المؤهل العلمي" to { contract: Contract, value: String -> contract.copy(educationLevel = value) },
            "اسم المصرف" to { contract: Contract, value: String -> contract.copy(bankName = value) },
            "رقم الحساب" to { contract: Contract, value: String -> contract.copy(accountNumber = value) },
            "الرقم الاشاري" to { contract: Contract, value: String -> contract.copy(reference = value) },
            "الملاحظات" to { contract: Contract, value: String -> contract.copy(notes = value) },
            )
        var contracts = emptyList<Contract>()
        try {
            contracts = xlsxToListOf(filePath, {
                Contract("", "", "", "", "", "", "", "", "", "", "", "", AgeGroup.UnderEightTeen, "","")
            }, map)
            filePrintLn(contracts.toString())
        }catch (e:Exception){
            filePrintLn("error import ${e.localizedMessage}")
        }
        emit(contracts)
    }
}