package features.courses.data.repository

import features.courses.domain.model.Course
import features.courses.domain.model.result1
import features.courses.domain.model.result2
import features.courses.domain.repository.CourseXlsxRepository
import features.sons_of_officers.domain.model.result1
import features.sons_of_officers.domain.model.result2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileOutputStream

class CoursesXlsxImpl: CourseXlsxRepository {

    override fun printCoursesToXlsxFile(courses: List<Course>, filePath:String, headers:List<String>): Flow<Boolean> = flow {
        val workbook = XSSFWorkbook()
        try {
            val sheet = workbook.createSheet("Persons")
            val headerRow = sheet.createRow(0)
            headers.forEachIndexed{ index: Int, headerName: String ->
                headerRow.createCell(index).setCellValue(headerName)
            }
            // ... create other header cells
            courses.forEachIndexed { index, course ->
                val row = sheet.createRow(index + 1)
                val testResult = if (course.result1() && !course.result2()) "لائق صحيا" else if(!course.result1() && course.result2()) "غير لائق صحيا" else "لاتوجد"

                headers.forEachIndexed { cellIndex: Int, headerName: String ->
                    when(headerName){
                        "رقم الملف" -> {
                            row.createCell(cellIndex).setCellValue(course.fileNumber)
                        }
                        "الاسم رباعي" -> {
                            row.createCell(cellIndex).setCellValue(course.name)
                        }
                        "الرقم الوطني" -> {
                            row.createCell(cellIndex).setCellValue(course.libyaId)
                        }
                        "اسم الام" -> {
                            row.createCell(cellIndex).setCellValue(course.motherName)
                        }
                        "المؤهل العلمي" -> {
                            row.createCell(cellIndex).setCellValue(course.educationLevel)
                        }
                        "المدينة" -> {
                            row.createCell(cellIndex).setCellValue(course.city)
                        }
                        "رقم الهاتف" -> {
                            row.createCell(cellIndex).setCellValue(course.phoneNumber)
                        }
                        "القائم بالتجنيد" -> {
                            row.createCell(cellIndex).setCellValue(course.recruiter)
                        }
                        "النتيجة" -> {
                            row.createCell(cellIndex).setCellValue(testResult.toString())
                        }
                        "اللجنة" -> {
                            row.createCell(cellIndex).setCellValue(course.commission)
                        }
                        "الملاحظات" -> {
                            row.createCell(cellIndex).setCellValue(course.notes)
                        }
                        else -> {}
                    }
                }
            }

            FileOutputStream("$filePath/الدورات.xlsx").use { outputStream ->
                workbook.write(outputStream)
                workbook.close()
            }
            emit(true)
        } catch (e: Exception) {
            workbook.close()
            emit(false)
        }
    }.flowOn(Dispatchers.IO)

    override fun getCoursesFromXlsxFile(path: String): Flow<List<Course>> {
        TODO("Not yet implemented")
    }
}