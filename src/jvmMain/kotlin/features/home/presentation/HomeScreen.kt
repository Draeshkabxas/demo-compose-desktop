package features.home.presentation

import AlertSystem.presentation.showErrorMessage
import AlertSystem.presentation.showSuccessMessage
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.component.*
import features.courses.presentation.add_courses.CourseInfoFormEvent
import navcontroller.NavController
import org.koin.compose.koinInject
import styles.AppColors
import styles.CairoTypography
import utils.getUserAuth

@Composable
fun HomeScreen(
    navController: NavController<Screens>,
    viewModel:HomeViewModel = koinInject()
) {
    val userAuthSystem = getUserAuth()
    var superAdminPermission = userAuthSystem.canChangeAccountsPermission()
    var canEditPermission =userAuthSystem.canEdit()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var showSaveBackupDirectoryPathDialog by remember { mutableStateOf(false) }
        var showGetBackupDirectoryPathDialog by remember { mutableStateOf(false) }
        HeadLineWithDate(text = "الصفحة الرئيسية ", date ="1/7/2023  1:30:36 PM" )

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.padding( start = 16.dp)
                ) {
                    if (canEditPermission){

                        GradientButton(
                            text = "حفظ نسخة احتياطية للنظام",
                            icon = Icons.Default.Save,
                            onClick = {
                                showSaveBackupDirectoryPathDialog = true
                            },
                            colors = AppColors.blueGradient
                        )
                        if (showSaveBackupDirectoryPathDialog) {
                            DirectoryDialog(
                                onApproved = { filePath ->
                                    viewModel.saveBackupTheDB(
                                        dirPath = filePath,
                                        onError = {
                                                it.showErrorMessage()
                                        },
                                        onLoading = {},
                                        onSuccess = {
                                            "تمت عملية النسخ الاحتياطي بنجاح".showSuccessMessage()
                                        })
                                    showSaveBackupDirectoryPathDialog = false
                                },
                                onCanceled = {
                                    showSaveBackupDirectoryPathDialog = false
                                    ("لم يتم الحصول على مسار الذي تريد الطباعة فيه").showErrorMessage()
                                },
                                onError = {
                                    ("لم يتم الحصول على مسار الذي تريد الطباعة فيه").showErrorMessage()
                                }
                            )
                        }
                    }
                    if (superAdminPermission){
                        GradientButton(
                            text = "إسترداد نسخة احتياطية للنظام",
                            icon = Icons.Default.UploadFile,
                            onClick = {
                                showGetBackupDirectoryPathDialog = true
                            },
                            colors = AppColors.blueGradient
                        )
                        if (showGetBackupDirectoryPathDialog) {
                            DirectoryDialog(
                                onApproved = { filePath ->
                                    viewModel.getBackup(dirPath = filePath)
                                    showGetBackupDirectoryPathDialog = false
                                    ("تمت عملية تحميل النسخة الاحتياطية بنجاح").showSuccessMessage()
                                },
                                onCanceled = {
                                    showGetBackupDirectoryPathDialog = false
                                    ("لم يتم الحصول على مسار الذي تريد الطباعة فيه").showErrorMessage()
                                },
                                onError = {
                                    ("لم يتم الحصول على مسار الذي تريد الطباعة فيه").showErrorMessage()
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.size(0.dp,10.dp))
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("صممت المنظومة لإدارة ملفاتك والوصول لكافة تفاصيل الملفات مع التعديل والإضافة في كافة البيانات ", style = CairoTypography.h3, fontWeight = FontWeight.Bold)
                    Text("مع إضافة ميزة البحث وايضا فلترة البحث للحصول على نتائج أكتر دقة ", style = CairoTypography.h3, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.size(0.dp,10.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    RoundedImage(
                        "images/bg.jpg",
                        modifier = Modifier.padding(horizontal = 10.dp),
                        size = DpSize(1000.dp, 600.dp),
                    )
                }
                Spacer(modifier = Modifier.size(0.dp,10.dp))
            }
        }
    }
}