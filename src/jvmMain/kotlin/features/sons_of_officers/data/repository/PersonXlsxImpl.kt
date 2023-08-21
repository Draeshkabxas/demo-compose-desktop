package features.sons_of_officers.data.repository

import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.domain.model.result1
import features.sons_of_officers.domain.model.result2
import features.sons_of_officers.domain.repository.PersonXlsxRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileOutputStream

class PersonXlsxImpl: PersonXlsxRepository {

    override fun printPersonsToXlsxFile(persons: List<Person>, filePath:String,headers:List<String>): Flow<Boolean> = flow {
        val workbook = XSSFWorkbook()
        try {
            val sheet = workbook.createSheet("Persons")
            val headers = headers.reversed()
            val headerRow = sheet.createRow(0)
            headers.forEachIndexed{ index: Int, headerName: String ->
                headerRow.createCell(index).setCellValue(headerName)
            }
            // ... create other header cells
            persons.forEachIndexed { index, person ->
                val row = sheet.createRow(index + 1)
                val testResult = if (person.result1() && !person.result2()) "لائق صحيا" else if(!person.result1() && person.result2()) "غير لائق صحيا" else "لاتوجد"

                headers.forEachIndexed { cellIndex: Int, headerName: String ->
                    when(headerName){
                        "رقم الملف" -> {
                            row.createCell(cellIndex).setCellValue(person.fileNumber)
                        }
                        "الاسم رباعي" -> {
                            row.createCell(cellIndex).setCellValue(person.name)
                        }
                        "الرقم الوطني" -> {
                            row.createCell(cellIndex).setCellValue(person.libyaId)
                        }
                        "اسم الام" -> {
                            row.createCell(cellIndex).setCellValue(person.motherName)
                        }
                        "المؤهل العلمي" -> {
                            row.createCell(cellIndex).setCellValue(person.educationLevel)
                        }
                        "المدينة" -> {
                            row.createCell(cellIndex).setCellValue(person.city)
                        }
                        "رقم الهاتف" -> {
                            row.createCell(cellIndex).setCellValue(person.phoneNumber)
                        }
                        "القائم بالتجنيد" -> {
                            row.createCell(cellIndex).setCellValue(person.recruiter)
                        }
                        "النتيجة" -> {
                            row.createCell(cellIndex).setCellValue(testResult.toString())
                        }
                        "اللجنة" -> {
                            row.createCell(cellIndex).setCellValue(person.commission)
                        }
                        "الملاحظات" -> {
                            row.createCell(cellIndex).setCellValue(person.notes)
                        }
                        else -> {}
                    }
                }
            }

            FileOutputStream("$filePath/ابناءالضباط.xlsx").use { outputStream ->
                workbook.write(outputStream)
                workbook.close()
            }
            emit(true)
        } catch (e: Exception) {
            workbook.close()
            emit(false)
        }
    }.flowOn(Dispatchers.IO)

    override fun getPersonsFromXlsxFile(path: String): Flow<List<Person>> {
        TODO("Not yet implemented")
    }
}