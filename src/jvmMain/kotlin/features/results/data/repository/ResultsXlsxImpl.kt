package features.results.data.repository

import features.results.domain.model.Results
import features.results.domain.repository.ResultsXlsxRepository

//import features.contracts.domain.model.result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileOutputStream

class ResultsXlsxImpl: ResultsXlsxRepository {

    override fun printResultsToXlsxFile(results: List<Results>, filePath:String, headers:List<String>): Flow<Boolean> = flow {
        val workbook = XSSFWorkbook()
        try {
            val sheet = workbook.createSheet("Persons")
            val headerRow = sheet.createRow(0)
            headers.forEachIndexed{ index: Int, headerName: String ->
                headerRow.createCell(index).setCellValue(headerName)
            }
            // ... create other header cells
            results.forEachIndexed { index, result ->
                val row = sheet.createRow(index + 1)
                headers.forEachIndexed { cellIndex: Int, headerName: String ->
                    when(headerName.trim()){
//                        "رقم الملف" -> {
//                            row.createCell(cellIndex).setCellValue(result.fileNumber)
//                        }
                        "الاسم رباعي" -> {
                            row.createCell(cellIndex).setCellValue(result.name)
                        }
//                        "اسم الام" -> {
//                            row.createCell(cellIndex).setCellValue(result.motherName)
//                        }
//                        "المؤهل العلمي" -> {
//                            row.createCell(cellIndex).setCellValue(result.educationLevel)
//                        }
                        "رقم الهاتف" -> {
                            row.createCell(cellIndex).setCellValue(result.phoneNumber)
                        }
                        "نتيجة التحاليل" -> {
                            row.createCell(cellIndex).setCellValue(result.result)
                        }
                        "تاريخ التحليل" -> {
                            row.createCell(cellIndex).setCellValue(result.date)
                        }

//                        "جنسية الام" -> {
//                            row.createCell(cellIndex).setCellValue(result.motherNationality)
//                        }

//                        "اسم المصرف"-> {
//                            row.createCell(cellIndex).setCellValue(result.bankName)
//                        }
//                        "رقم الحساب"-> {
//                            row.createCell(cellIndex).setCellValue(result.accountNumber)
//                        }
                        "الملاحظات"-> {
                            row.createCell(cellIndex).setCellValue(result.notes)
                        }
                        else -> {}
                    }
                }
            }

            FileOutputStream("$filePath/نتائج التحاليل.xlsx").use { outputStream ->
                workbook.write(outputStream)
                workbook.close()
            }
            emit(true)
        } catch (e: Exception) {
            workbook.close()
            emit(false)
        }
    }.flowOn(Dispatchers.IO)


    override fun getResultsFromXlsxFile(path: String): Flow<List<Results>> {
        TODO("Not yet implemented")
    }
}