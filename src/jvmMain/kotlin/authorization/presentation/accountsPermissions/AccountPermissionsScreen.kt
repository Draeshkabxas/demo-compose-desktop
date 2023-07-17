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
import authorization.domain.model.*
import authorization.domain.model.Jobs.*
import authorization.domain.model.Systems.*
import common.component.CustomCheckboxWithLabel
import common.component.HeadLineWithDate
import common.component.Screens
import org.koin.compose.koinInject
import styles.AppColors.blue
import styles.CairoTypography


@Composable
fun AccountsPermissionsScreen(
    navController: NavController<Screens>,
    viewModel: AccountPermissionViewModel = koinInject()
) {

    var usersList by remember { mutableStateOf<List<User>>(emptyList()) }



    LaunchedEffect(key1 = true) {
        viewModel.usersDataFlow.collect { users ->
            usersList = users
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HeadLineWithDate(text = "حسابات الموظفين", date = "1/7/2023  1:30:36 PM")
        Button(
            shape = RoundedCornerShape(100.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
            modifier = Modifier.padding(horizontal = 0.dp, vertical = 0.dp),
            contentPadding = PaddingValues(0.dp),
            onClick = {
                viewModel.refreshUsersData()
            },
        ) {
            Icon(
                imageVector = Icons.Default.RestartAlt,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
        ) {
            LazyColumn(Modifier.padding(10.dp)) {
                items(usersList) { user ->
                    if (user.job != SuperAdmin) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().padding(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f).padding(end = 8.dp)
                            ) {
                                Text(
                                    text = " اسم الحساب : ${user.name}",
                                    style = CairoTypography.h2,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "الصلاحيات", style = CairoTypography.h3, fontWeight = FontWeight.SemiBold)
                                var isAdminChecked by remember { mutableStateOf(user.job == Admin) }

                                CustomCheckboxWithLabel(
                                    label = "إمكانية الاضافة والتعديل",
                                    checked = isAdminChecked,
                                    onCheckedChange = {
                                        isAdminChecked = it
                                        if (it) user.changeJob(Admin)
                                    },
                                    modifier = Modifier.padding(vertical = 0.dp),
                                    labelColor = Color.Black,
                                    checkboxColor = blue
                                )

                                var isViewerChecked by remember { mutableStateOf(user.job == Viewer) }

                                CustomCheckboxWithLabel(
                                    label = "عرض فقط",
                                    checked = isViewerChecked,
                                    onCheckedChange = {
                                        isViewerChecked = it
                                        if (it) user.changeJob(Viewer)
                                    },
                                    modifier = Modifier.padding(vertical = 0.dp),
                                    labelColor = Color.Black,
                                    checkboxColor = blue
                                )
                            }
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "المنظومات", style = CairoTypography.h3, fontWeight = FontWeight.SemiBold)

                                var isContractsChecked by remember { mutableStateOf(user.canAccessScreen(Contracts)) }

                                CustomCheckboxWithLabel(
                                    label = "منظومة العقود",
                                    checked = isContractsChecked,
                                    onCheckedChange = {
                                        isContractsChecked = it
                                        user.addOrRemoveScreenFromSystem(Contracts, it)
                                    },
                                    modifier = Modifier.padding(vertical = 0.dp),
                                    labelColor = Color.Black,
                                    checkboxColor = blue
                                )

                                var isCoursesChecked by remember { mutableStateOf(user.canAccessScreen(Courses)) }

                                CustomCheckboxWithLabel(
                                    label = "منظومة الدورات",
                                    checked = isCoursesChecked,
                                    onCheckedChange = {
                                        isCoursesChecked = it
                                        user.addOrRemoveScreenFromSystem(Courses, it)
                                    },
                                    modifier = Modifier.padding(vertical = 0.dp),
                                    labelColor = Color.Black,
                                    checkboxColor = blue
                                )

                                var isSonsOfOfficersChecked by remember {
                                    mutableStateOf(
                                        user.canAccessScreen(
                                            SonsOfOfficers
                                        )
                                    )
                                }

                                CustomCheckboxWithLabel(
                                    label = "منظومة ابناء الضباط",
                                    checked = isSonsOfOfficersChecked,
                                    onCheckedChange = {
                                        isSonsOfOfficersChecked = it
                                        user.addOrRemoveScreenFromSystem(SonsOfOfficers, it)
                                    },
                                    modifier = Modifier.padding(vertical = 0.dp),
                                    labelColor = Color.Black,
                                    checkboxColor = blue
                                )
                            }
                            Button(
                                onClick = {
                                    viewModel.updateUser(user)
                                },
                                shape = RoundedCornerShape(24.dp),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                Text(text = "موافق", style = CairoTypography.body2)
                            }
                            IconButton(
                                onClick = {
                                    // Handle delete button click
                                    viewModel.deleteUser(user)
                                    viewModel.refreshUsersData()
                                },
                                modifier = Modifier.padding(horizontal = 8.dp),
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }
}


