package license.presentation

import AuthScreen
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Upload
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import navcontroller.NavController
import org.koin.compose.koinInject
import styles.AppColors
import styles.AppColors.white
import java.awt.FileDialog
import java.awt.Frame

@Composable
fun AppLicensesScreen(
    navController: NavController<String>?,
    modifier: Modifier = Modifier,
    viewModel:AppLicenseViewModel = koinInject()
) {
    if (!viewModel.showAppLicense.value){
        navController?.navigate(AuthScreen.LoginAuthScreen.name)
    }
    val fileDialog = remember { FileDialog(Frame()) }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .shadow(3.dp, RoundedCornerShape(20.dp)),
        color = AppColors.background,
        shape = RoundedCornerShape(20.dp), //window has round corners now
        elevation = 40.dp
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("حمل ملف مفتاح تشغيل المنظومة")
            if (!viewModel.showOpenSystem.value) {
                OutlinedButton(
                    shape = RoundedCornerShape(20),
                    border = BorderStroke(2.dp, AppColors.primary),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = AppColors.primary),
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        fileDialog.isVisible = true
                        val secretKeyfilePath = fileDialog.directory + fileDialog.file
                        viewModel.activateLicense(secretKeyfilePath)
                    },
                ) {
                    Icon(Icons.Default.Upload, contentDescription = "upload")
                }
            }else{
                OutlinedButton(
                    shape = RoundedCornerShape(20),
                    border = BorderStroke(2.dp, AppColors.primary),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = AppColors.primary),
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                       viewModel.openSystem()
                    },
                ) {
                    Text("تشغيل المنظومة")
                }
            }
            Text(text = viewModel.message.value)

        }
    }
}

@Preview
@Composable
private fun PreviewAppLicenses() {
    AppLicensesScreen(null)
}