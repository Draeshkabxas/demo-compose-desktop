package authorization.presentation.accountsPermissions


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import navcontroller.NavController
import androidx.compose.material.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import authorization.domain.model.Jobs
import authorization.domain.model.Systems
import authorization.domain.model.User
import common.component.CheckboxWithLabel
import common.component.CustomCheckboxWithLabel
import common.component.HeadLineWithDate
import common.component.Screens
import features.contracts.domain.model.Contract
import features.contracts.presentation.contracts.ContractsScreenViewModel
import org.koin.compose.koinInject
import styles.AppColors.blue
import styles.CairoTypography


@Composable
fun AccountsPermissionsScreen(
    navController: NavController<Screens>,
    viewModel: ContractsScreenViewModel = koinInject()
) {
    val widths =
        listOf(40.dp, 82.dp, 150.dp, 120.dp, 130.dp, 85.dp, 115.dp, 75.dp, 110.dp, 100.dp, 100.dp, 95.dp, 85.dp, 80.dp)
    val headers = listOf(
        "رقم", "رقم الملف", "الإسم رباعي",
        "الرقم الوطني", "إسم الأم", "جنسية الأم",
        "المؤهل العلمي", "المدينة", "رقم الهاتف",
        "التبعية", "إسم المصرف", "رقم الحساب",
        "الملاحظات", "تعديل"
    )

    var contractsData by remember { mutableStateOf<List<Contract>>(emptyList()) }
    var showPrintListDialog by remember { mutableStateOf(false) }
    var showPrintDirectoryPathDialog by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = true) {
        viewModel.contractsDataFlow.collect { people ->
            contractsData = people
        }
    }
    val usersList = listOf(
        User(
            id = "1",
            name = "John Doe",
            password = "password",
            job = Jobs.Admin,
            systems = listOf(Systems.Contracts, Systems.Courses)
        ),
        User(
            id = "2",
            name = "Jane Smith",
            password = "password",
            job = Jobs.Viewer,
            systems = listOf(Systems.Courses, Systems.SonsOfOfficers)
        ),

        // Add more users here
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HeadLineWithDate(text = "حسابات الموظفين", date = "1/7/2023  1:30:36 PM")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
        ) {
            LazyColumn(Modifier.padding(10.dp)) {
                items(usersList) { user ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f).padding(end = 8.dp)
                        ) {
                            Text(text = " اسم الحساب : ${user.name}",style = CairoTypography.h2, fontWeight = FontWeight.SemiBold)
                        }
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = "الصلاحيات",style = CairoTypography.h3, fontWeight = FontWeight.SemiBold)
                            var isAdminChecked by remember { mutableStateOf(false) }

                            CustomCheckboxWithLabel(
                                label = "إمكانية الاضافة والتعديل",
                                checked = isAdminChecked,
                                onCheckedChange = { isAdminChecked = it },
                                modifier = Modifier.padding(vertical = 0.dp),
                                labelColor = Color.Black,
                                checkboxColor = blue
                            )
//                            CheckboxWithLabel(
//                                label = "Admin",
//                                checked = user.job == Jobs.Admin
//                            ) { checked ->
//                                user.job = if (checked) Jobs.Admin else Jobs.Viewer
//                            }
//                            CheckboxWithLabel(
//                                label = "Viewer",
//                                checked = user.job == Jobs.Viewer
//                            ) { checked ->
//                                user.job = if (checked) Jobs.Viewer else Jobs.Admin
//                            }
                            var isViewerChecked by remember { mutableStateOf(false) }

                            CustomCheckboxWithLabel(
                                label = "عرض فقط",
                                checked = isViewerChecked,
                                onCheckedChange = { isViewerChecked = it },
                                modifier = Modifier.padding(vertical = 0.dp),
                                labelColor = Color.Black,
                                checkboxColor = blue
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = "المنظومات",style = CairoTypography.h3, fontWeight = FontWeight.SemiBold)
//                            CheckboxWithLabel(
//                                label = "Contracts",
//                                checked = user.systems.contains(Systems.Contracts)
//                            ) { checked ->
//                                if (checked) user.systems += Systems.Contracts
//                                else user.systems -= Systems.Contracts
//                            }
                            var isContractsChecked by remember { mutableStateOf(false) }

                            CustomCheckboxWithLabel(
                                label = "منظومة العقود",
                                checked = isContractsChecked,
                                onCheckedChange = { isContractsChecked = it },
                                modifier = Modifier.padding(vertical = 0.dp),
                                labelColor = Color.Black,
                                checkboxColor = blue
                            )
//                            CheckboxWithLabel(
//                                label = "Courses",
//                                checked = user.systems.contains(Systems.Courses)
//                            ) { checked ->
//                                if (checked) user.systems += Systems.Courses
//                                else user.systems -= Systems.Courses
//                            }
                            var isCoursesChecked by remember { mutableStateOf(false) }

                            CustomCheckboxWithLabel(
                                label = "منظومة الدورات",
                                checked = isCoursesChecked,
                                onCheckedChange = { isCoursesChecked = it },
                                modifier = Modifier.padding(vertical = 0.dp),
                                labelColor = Color.Black,
                                checkboxColor = blue
                            )
//                            CheckboxWithLabel(
//                                label = "Sons of Officers",
//                                checked = user.systems.contains(Systems.SonsOfOfficers)
//                            ) { checked ->
//                                if (checked) user.systems += Systems.SonsOfOfficers
//                                else user.systems -= Systems.SonsOfOfficers
//                            }
                            var isSonsofOfficersChecked by remember { mutableStateOf(false) }

                            CustomCheckboxWithLabel(
                                label = "منظومة ابناء الضباط",
                                checked = isSonsofOfficersChecked,
                                onCheckedChange = { isSonsofOfficersChecked = it },
                                modifier = Modifier.padding(vertical = 0.dp),
                                labelColor = Color.Black,
                                checkboxColor = blue
                            )
                        }
                        Button(
                            onClick = {
                                // Handle confirm button click
                            },
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Text(text = "موافق",style= CairoTypography.body2)
                        }
                        IconButton(
                            onClick = {
                                // Handle delete button click
                            },
                            modifier = Modifier.padding(horizontal = 8.dp),
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete",tint= Color.Red)
                        }
                    }
                }
            }        }
    }
}


