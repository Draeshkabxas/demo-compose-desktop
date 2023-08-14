package authorization.presentation.login

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import authorization.presentation.component.PasswordTextField
import authorization.presentation.component.UserNameTextField
import authorization.domain.model.Jobs.None
import authorization.domain.model.Jobs.Viewer
import authorization.domain.model.User
import org.koin.compose.koinInject
import authorization.domain.repository.AppCloseRepository
import common.component.*
import utils.Resource
import navcontroller.NavController
import styles.CairoTypography
import styles.AppColors.background
import styles.AppColors.primary
import styles.AppColors.white
import utils.getUserAuth
import kotlin.system.exitProcess

@Composable
fun LoginScreen(
    navController: NavController<String>,
    viewModel: LoginViewModel = koinInject(),
    appClose: AppCloseRepository = koinInject(),
) {
    val userAuthSystem = getUserAuth()
    println("your in login screen")
    val userName = mutableStateOf<String>("")
    val password = mutableStateOf<String>("")
    Surface(
        modifier = Modifier
            .size(1000.dp)
            .padding(5.dp)
            .shadow(3.dp, RoundedCornerShape(20.dp)),
        color = background,
        shape = RoundedCornerShape(20.dp), //window has round corners now
        elevation = 40.dp
    ) {
        val state = viewModel.loginState.value
        var showDialog by remember { mutableStateOf(false) }

        when (state) {
//            is Resource.Error -> {
//                println("error: ${state.message}")
//            }

            is Resource.Error -> {
                state.message?.let {
                    if (showDialog) {
                        AlertDialogSimple(
                            message = "the login eroor"
                        ) {
//                            showDialog = false
                        }
                    }

//                    CustomAlertDialog(
//                        title = "حدث خطا عند محاولة تسجيل دخولك",
//                        message = it,
//                        onDismiss = { /* Do something when the dialog is dismissed */ }
//                    )
                }
            }

            is Resource.Loading -> {
                if (state.data == null) {
                    LoadingDialog {
                        Text("Loading... ")
                    }
                }
            }

            is Resource.Success -> {
                if (state.data != null && state.data.job != None){
                    if (showDialog) {
                        AlertDialogSimple(
                            message = "تم تسجيل دخولك بنجاح"
                        ) {
//                            showDialog = true
                        }
                    }
                    userAuthSystem.currentUser=state.data
                    navController.navigate(AuthScreen.SystemScreen.name)

                }
            }

            else -> {}
        }
        CompositionLocalProvider(
            LocalLayoutDirection provides LayoutDirection.Rtl // Set layout direction to RTL
        ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxHeight()
                    .padding(horizontal = 35.dp),
                horizontalAlignment = Alignment.Start
            ) {
                IconButton(onClick = { exitProcess(0) }) {
                    Image(
                        painter = painterResource("images/exit.svg"),
                        contentDescription = "Exit Application",
                        modifier = Modifier.size(22.dp)
                    )
                }
                Image(
                    painter = painterResource("images/welcome.svg"),
                    contentDescription = "welcome.png",
                    modifier = Modifier.size(250.dp, 170.dp)
                )
                Text(
                    " الرجاء إدخال إسم المستخدم و كلمة السر",
                    style = CairoTypography.h3,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.size(30.dp))
                UserNameTextField(
                    "",
                    { name -> userName.value = name },
                    errorMessage = "",
                    onNextChange = { print(it) })
                PasswordTextField(
                    "",
                    { passwordText -> password.value = passwordText },
                    errorMessage = "",
                    onNextChange = { print(it) },
                    modifier = Modifier.padding(vertical = 25.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    RoundedButton({
                        viewModel.login(User("", userName.value, password.value, Viewer, listOf()))
                        showDialog = true

                    }, "تسجيل الدخول")
                    OutlineRoundedButton({
                        navController.navigate(AuthScreen.SignupAuthScreen.name)
                    }, "إنشاء حساب")
                }
                Text(
                    "ملاحظه: عند إنشاء حسابك لأول مرة يرجى التواصل مع المسؤول لتفعيل حسابك",
                    style = CairoTypography.body1,
                    textAlign = TextAlign.Start,
                    color = Color.Red
//                fontWeight = FontWeight.SemiBold
                )
            }

            RoundedImage("images/cover.jpg", modifier = Modifier.padding(horizontal = 35.dp))
        }
    }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoadingDialog(
    size: DpSize= DpSize(350.dp, 250.dp),
    content: @Composable() () -> Unit,
) {
    AlertDialog(
        modifier = Modifier.size(size),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.Transparent,
       onDismissRequest = {},
        buttons={
           Card(
               modifier = Modifier.size(size),
             shape = RoundedCornerShape(20.dp),
             elevation = 5.dp,
               backgroundColor = white,
           ) {
               Column(
                   verticalArrangement = Arrangement.SpaceAround,
                   horizontalAlignment = Alignment.CenterHorizontally
               ) {
                   println("loading dialog show")
                   CircularProgressIndicator(
                       modifier = Modifier.size(size.width/4, size.height/4),
                       color = primary,
                       strokeWidth = 6.dp
                   )
                  content()
               }
           }
       }
    )
}

@Preview
@Composable
fun LoginScreenPreview() {
    //LoginScreen()
    LoadingDialog {  }
}
