package features.results.data.repository

import common.domain.xlsxToListOf
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
                        "الاسم رباعي" -> {
                            row.createCell(cellIndex).setCellValue(result.name)
                        }
                        "رقم الهاتف" -> {
                            row.createCell(cellIndex).setCellValue(result.phoneNumber)
                        }
                        "نتائج التحاليل" -> {
                            row.createCell(cellIndex).setCellValue(result.result)
                        }
                        "تاريخ التحاليل" -> {
                            row.createCell(cellIndex).setCellValue(result.date)
                        }
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


    override fun getResultsFromXlsxFile(path: String): Flow<List<Results>> = flow {
        val map: Map<String, (Results, String) -> Results> = mapOf(
            "الاسم" to { result:Results, value: String -> result.copy(name = value) },
            "رقم الهاتف" to { result:Results, value: String -> result.copy(phoneNumber = value) },
            "التاريخ" to { result:Results, value: String -> result.copy(date = value) },
            "الملاحظات" to { result:Results, value: String -> result.copy(notes = value) },
            "النتيجة" to { result:Results, value: String -> result.copy(result = value) },
            )
        val results = xlsxToListOf(path, {
            Results(id = "", name = "", phoneNumber = "", result = "", date = "", notes = "")
        }, map)
        emit(results)
    }
}