package common.component

import utils.Resource
import java.awt.FileDialog
import java.awt.Frame

fun GetFilePathDialog(
    onError:(String) -> Unit,
    onSuccess: (String)-> Unit
){
    try {
        var filePath: String? = null
        // Create a FileDialog object with the current window as the parent
        val fileDialog = FileDialog(Frame())

        // Set the mode of the file dialog to load
        fileDialog.mode = FileDialog.LOAD

        // Set the title of the file dialog
        fileDialog.title = "Select a file"

        // Set the file dialog to stay on top
        fileDialog.isAlwaysOnTop = true
        fileDialog.isFocusTraversalPolicyProvider = true


        // Set the visibility of the file dialog to true
        fileDialog.isVisible = true
        fileDialog.setFilenameFilter { _, name ->
            name.endsWith(".xlsx")
        }
        // Set the file dialog to stay on top
        fileDialog.isAlwaysOnTop = true
        fileDialog.isAutoRequestFocus = true
        fileDialog.isFocusCycleRoot = true

        filePath = fileDialog.directory + fileDialog.file
        filePath?.let {
            onSuccess(it)
        }
    } catch (e: Exception) {
        onError("يرجي اختيار ملف من نوع xlsx الذي يحتوي على البينات المطلوبة")
    }
}