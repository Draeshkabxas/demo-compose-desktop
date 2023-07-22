package features.home.presentation

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

//                    Text(
//                        "صممة المنظومة لإدارة عقاراتك من تنظيم لعمليات البيع مع الزبائن ومتابعة وحساب تكاليف البناء وايضا حصر العمال ومتابعة تكاليف خدماتهم  ",
//                        modifier = Modifier.padding(top = 32.dp, start = 16.dp),
//                        style = CairoTypography.h3,
////                    color = Color.White
//                    )
                    if (canEditPermission){
//                        Button(
//                            onClick = {
//                                showSaveBackupDirectoryPathDialog = true
//                            }
//                        ) {
//                            Text("حفظ نسخة احتياطية لنظام", style = CairoTypography.body1)
//                        }
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
                                        onError = {},
                                        onLoading = {},
                                        onSuccess = {})
                                    showSaveBackupDirectoryPathDialog = false
                                },
                                onCanceled = {
                                    showSaveBackupDirectoryPathDialog = false
                                    println("on canceled")
                                },
                                onError = {
                                    println("on onError")
                                }
                            )
                        }
                    }
                    if (superAdminPermission){
//                        Button(
//                            onClick = {
//                                showGetBackupDirectoryPathDialog = true
//                            },
//                            modifier = Modifier.padding(start = 8.dp)
//                        ) {
//                            Text("إسترداد نسخة احتياطية لنظام", style = CairoTypography.body1)
//                        }
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
                                },
                                onCanceled = {
                                    showGetBackupDirectoryPathDialog = false
                                    println("on canceled")
                                },
                                onError = {
                                    println("on onError")
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
//                    text =                         "صممة المنظومة لإدارة عقاراتك من تنظيم لعمليات البيع مع الزبائن ومتابعة وحساب تكاليف البناء وايضا حصر العمال ومتابعة تكاليف خدماتهم  ",
//                    textStyle = CairoTypography.h3,
                    )
                }
                Spacer(modifier = Modifier.size(0.dp,10.dp))



            }
        }




    }
}