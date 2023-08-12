package common.domain

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File

// Define a function that converts a cell value to a string
fun cellToString(cell: Cell?): String {
    return DataFormatter().formatCellValue(cell)
}

fun <T> sheetToListOf(sheet: Sheet, contractFactory: () -> T, map: Map<String, (T, String) -> T>): List<T> {
    // Create an empty list to store the contracts
    val contracts = mutableListOf<T>()

    // Get the header row of the sheet
    val header = sheet.getRow(0)

    // Loop through the rows of the sheet, skipping the header row
    for (i in 1 until sheet.lastRowNum + 1) {
        // Get the current row
        val row = sheet.getRow(i)

        // Create a new contract object using the contractFactory
        var contract = contractFactory()

        // Loop through the cells of the row
        for (j in 0 until row.physicalNumberOfCells) {
            // Get the current cell and its value
            val cell = row.getCell(j)
            val value = cellToString(cell)

            // Get the column name from the header row
            val columnName = cellToString(header.getCell(j))

            // Check if the column name is in the map
            if (columnName in map) {
                // Get the lambda expression from the map
                val lambda = map[columnName]

                // Invoke the lambda expression with the contract object and the value
                lambda?.let {
                    contract = it(contract, value)
                }
            }
        }

        // Add the contract to the list
        contracts.add(contract)
    }

    // Return the list of contracts
    return contracts
}


// Define a function that reads an xlsx file and returns a list of contracts
fun <T> xlsxToListOf(filename: String, objectFactory: () -> T, map: Map<String, (T, String) -> T>): List<T> {
    // Create a workbook object from the file
    val workbook = WorkbookFactory.create(File(filename))

    // Get the first sheet of the workbook
    val sheet = workbook.getSheetAt(0)

    // Create a cell style object and set the font
    val style = workbook.createCellStyle()
    val format = workbook.createDataFormat()
    style.dataFormat = format.getFormat("#")

    // Loop through the rows and cells and apply the style
    for (row in sheet) {
        for (cell in row) {
            cell.cellStyle = style
        }
    }

    // Set the direction of the sheet to right-to-left
    sheet.isRightToLeft = true

    // Convert the sheet to a list of contracts and return it
    val objectsList = sheetToListOf(sheet, objectFactory, map)
    workbook.close()
    return objectsList
}
