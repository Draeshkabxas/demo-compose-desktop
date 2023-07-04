package features.sons_of_officers.data.repository

import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.domain.repository.PersonXlsxRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileOutputStream

class PersonXlsxImpl: PersonXlsxRepository {

    override fun printPersonsToXlsxFile(persons: List<Person>,filePath:String): Flow<Boolean> = flow {
        try {
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet("Persons")
            val headerRow = sheet.createRow(0)
            headerRow.createCell(0).setCellValue("الرقم التسلسلي")
            headerRow.createCell(1).setCellValue("الاسم رباعي")
            headerRow.createCell(2).setCellValue("اسم الام")


            headerRow.createCell(1).setCellValue("رقم الملف")
            headerRow.createCell(2).setCellValue("الرقم الوطني")

            headerRow.createCell(1).setCellValue("رقم الهاتف")
            headerRow.createCell(2).setCellValue("المؤهل العلمي")

            headerRow.createCell(1).setCellValue("القائم بالتجنيد")
            headerRow.createCell(2).setCellValue("المدينة")

            headerRow.createCell(1).setCellValue("المصوغات المطلوبة")
            headerRow.createCell(2).setCellValue("الاجراءات")

            // ... create other header cells

            persons.forEachIndexed { index, person ->
                val row = sheet.createRow(index + 1)
                row.createCell(0).setCellValue(person.id)
                row.createCell(1).setCellValue(person.name)
                row.createCell(2).setCellValue(person.motherName)
                // ... set other cell values
            }

            FileOutputStream("$filePath/persons.xlsx").use { outputStream ->
                workbook.write(outputStream)
            }
            emit(true)
        } catch (e: Exception) {
            emit(false)
        }
    }.flowOn(Dispatchers.IO)

    override fun getPersonsFromXlsxFile(path: String): Flow<List<Person>> {
        TODO("Not yet implemented")
    }
}