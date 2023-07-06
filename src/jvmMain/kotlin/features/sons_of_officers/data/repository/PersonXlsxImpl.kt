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

    override fun printPersonsToXlsxFile(persons: List<Person>, filePath:String): Flow<Boolean> = flow {
        val workbook = XSSFWorkbook()
        try {
            val sheet = workbook.createSheet("Persons")
            val headerRow = sheet.createRow(0)
            headerRow.createCell(0).setCellValue("الرقم التسلسلي")
            headerRow.createCell(1).setCellValue("الاسم رباعي")
            headerRow.createCell(2).setCellValue("اسم الام")


            headerRow.createCell(3).setCellValue("رقم الملف")
            headerRow.createCell(4).setCellValue("الرقم الوطني")

            headerRow.createCell(5).setCellValue("رقم الهاتف")
            headerRow.createCell(6).setCellValue("المؤهل العلمي")

            headerRow.createCell(7).setCellValue("القائم بالتجنيد")
            headerRow.createCell(8).setCellValue("المدينة")

            headerRow.createCell(9).setCellValue("المصوغات المطلوبة")
            headerRow.createCell(10).setCellValue("الاجراءات")

            // ... create other header cells

            persons.forEachIndexed { index, person ->
                val row = sheet.createRow(index + 1)
                val properties = Person::class.java.declaredFields

                val values = properties.map { field ->
                    field.isAccessible = true
                    field.get(person)
                }

                values.forEachIndexed { index, value ->
                    row.createCell(index).setCellValue(value.toString())
                }
                row.createCell(9).setCellValue(person.justificationsRequire.filterValues { it }
                    .keys.joinToString(
                            separator = ", ",  // Delimiter between elements
                            prefix = "",     // Prefix before the list
                            postfix = ""     // Postfix after the list
                        )
                )
                row.createCell(10).setCellValue(person.procedures.filterValues { it }
                    .keys.joinToString(
                    separator = ", ",  // Delimiter between elements
                    prefix = "",     // Prefix before the list
                    postfix = ""     // Postfix after the list
                )
                )
                // ... set other cell values
            }

            FileOutputStream("$filePath/persons.xlsx").use { outputStream ->
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