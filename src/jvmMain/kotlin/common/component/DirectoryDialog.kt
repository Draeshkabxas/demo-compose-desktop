package common.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.UIManager

fun DirectoryDialog(
    onApproved:(String) ->Unit,
    onCanceled:() ->Unit,
    onError:(String) ->Unit,
){
    val scope = CoroutineScope(Dispatchers.IO)
    scope.launch {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
            val frame = JFrame()
            frame.isAlwaysOnTop = true
            val fileChooser = JFileChooser().apply {
                fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
            }
            frame.contentPane.add(fileChooser)
            when (val result = fileChooser.showOpenDialog(frame)) {
                JFileChooser.APPROVE_OPTION -> {
                    val directory = fileChooser.selectedFile
                    onApproved(directory.absolutePath)
                }
                JFileChooser.CANCEL_OPTION -> {
                    onCanceled()
                }
                JFileChooser.ERROR_OPTION -> {
                    onError(result.toString())
                }
            }
        }catch (e:Exception){
            onError(e.localizedMessage)
        }
    }

}