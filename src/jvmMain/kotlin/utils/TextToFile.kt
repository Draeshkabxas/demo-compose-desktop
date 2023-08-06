package utils

import java.io.*

fun createFileOnDesktop(fileContent: String, fileName: String = "error.text") {
    val desktopPath = System.getProperty("user.home") + File.separator + "Desktop"
    val file = File(desktopPath, fileName)
    try {
        file.writeText(fileContent)
        println("File '$fileName' created successfully on the desktop.")
    } catch (e: Exception) {
        println("An error occurred while creating the file: ${e.message}")
    }
}

fun clearFile(fileName: String = "error.text") {
    val desktopPath = "C:/Users/sabar/OneDrive/Desktop/" // System.getProperty("user.home") + File.separator + "Desktop"
    try {
        val file = File(desktopPath + fileName)
        if (file.exists()) {
            // File already exists, truncate its contents
            val randomAccessFile = RandomAccessFile(file, "rw")
            randomAccessFile.setLength(0)
            randomAccessFile.close()
        }
    } catch (e: Exception) {
        println("An error occurred while emptying the file: ${e.message}")
    }
}


fun filePrintLn(line: String, fileName: String = "error.text") {
    val desktopPath = "C:/Users/sabar/OneDrive/Desktop/" // System.getProperty("user.home") + File.separator + "Desktop"
    val file = File(desktopPath + fileName)

    try {
        if (!file.exists()) {
            file.createNewFile()
        }
        val fileWriter = FileWriter(file, true) // Append mode
        val bufferedWriter = BufferedWriter(fileWriter)

        bufferedWriter.write(line)
        bufferedWriter.newLine()

        bufferedWriter.close()
        fileWriter.close()

        println("Line appended to the file '$fileName' successfully.")
    } catch (e: Exception) {
        println("An error occurred while appending to the file: ${e.message}")
    }
}

fun filePrint(content: String, fileName: String = "error.text") {
    val desktopPath = System.getProperty("user.home") + File.separator + "Desktop"
    val file = File(desktopPath + fileName)
    try {
        if (!file.exists()) {
            file.createNewFile()
        }
        val fileWriter = FileWriter(file, true) // Append mode
        val bufferedWriter = BufferedWriter(fileWriter)

        bufferedWriter.write(content)

        bufferedWriter.close()
        fileWriter.close()

        println("Content appended to the file '$fileName' successfully.")
    } catch (e: Exception) {
        println("An error occurred while appending to the file: ${e.message}")
    }
}