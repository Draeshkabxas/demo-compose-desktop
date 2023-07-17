package features.contracts.data.repository

import features.contracts.domain.model.Contract
import features.contracts.domain.repository.ContractXlsxRepository
import features.sons_of_officers.domain.model.result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileOutputStream

class ContractXlsxImpl: ContractXlsxRepository {

    override fun printContractsToXlsxFile(contracts: List<Contract>, filePath:String, headers:List<String>): Flow<Boolean> = flow {
        val workbook = XSSFWorkbook()
        try {
            val sheet = workbook.createSheet("Persons")
            val headerRow = sheet.createRow(0)
            headers.forEachIndexed{ index: Int, headerName: String ->
                headerRow.createCell(index).setCellValue(headerName)
            }
            // ... create other header cells
            contracts.forEachIndexed { index, contract ->
                val row = sheet.createRow(index + 1)
                headers.forEachIndexed { cellIndex: Int, headerName: String ->
                    when(headerName.trim()){
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
                        "اسم المصرف"-> {
                            row.createCell(cellIndex).setCellValue(contract.bankName)
                        }
                        "رقم الحساب"-> {
                            row.createCell(cellIndex).setCellValue(contract.accountNumber)
                        }
                        "الرقم الوطني"-> {
                            row.createCell(cellIndex).setCellValue(contract.libyaId)
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
            workbook.close()
            emit(false)
        }
    }.flowOn(Dispatchers.IO)


    override fun getContractsFromXlsxFile(path: String): Flow<List<Contract>> {
        TODO("Not yet implemented")
    }
}