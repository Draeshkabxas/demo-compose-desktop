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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import common.component.OutlineRoundedButton
import common.component.RoundedButton
import common.component.RoundedImage
import authorization.presentation.component.PasswordTextField
import authorization.presentation.component.UserNameTextField
import authorization.domain.model.Jobs
import authorization.domain.model.User
import org.koin.compose.koinInject
import authorization.domain.repository.AppCloseRepository
import common.Resource
import navcontroller.NavController
import styles.CairoTypography
import styles.AppColors.background
import styles.AppColors.primary
import styles.AppColors.white

@Composable
fun LoginScreen(
    navController: NavController<String>,
    viewModel: LoginViewModel = koinInject(),
    appClose: AppCloseRepository = koinInject(),
) {
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
        val state=viewModel.loginState.value
        when(state) {
            is Resource.Error -> {
                println("error: ${state.message}")
            }
            is Resource.Loading -> {
                if(state.data == true){
                    LoadingDialog{
                        Text("Loading... ")
                    }
                }
            }
            is Resource.Success -> {
                navController.navigate(AuthScreen.SystemScreen.name)
            }
            else -> {}
        }
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxHeight()
                    .padding(horizontal = 35.dp),
            ) {
                Image(
                    painter = painterResource("images/welcome.svg"),
                    contentDescription = "welcome.png",
                    modifier = Modifier.size(250.dp, 170.dp)
                )
                Text(
                    "الرجاء ادخال اسم المستخدم\n" +
                            "و كلمة السر", style = CairoTypography.body1
                )
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
                        viewModel.login(User("", userName.value, password.value, Jobs.Viewer, listOf()))                    }, "تسجيل الدخول")
                    OutlineRoundedButton({
                        navController.navigate(AuthScreen.SignupAuthScreen.name)
                    }, "إنشاء حساب")
                }
            }
            RoundedImage("images/login-image.png", modifier = Modifier.padding(horizontal = 35.dp))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoadingDialog(
    size: DpSize= DpSize(400.dp, 320.dp),
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
                       strokeWidth = 4.dp
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
