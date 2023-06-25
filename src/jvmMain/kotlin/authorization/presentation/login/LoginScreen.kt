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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import common.component.OutlineRoundedButton
import common.component.RoundedButton
import common.component.RoundedImage
import authorization.presentation.component.PasswordTextField
import authorization.presentation.component.UserNameTextField
import authorization.data.model.User
import org.koin.compose.koinInject
import authorization.domain.repository.AppCloseRepository
import authorization.presentation.register.RegisterViewModel
import styles.CairoTypography
import styles.Colors.background

@Composable
fun LoginScreen(
    viewModel:  LoginViewModel= koinInject(),
    appClose: AppCloseRepository = koinInject(),
) {
    val userName = mutableStateOf<String>("")
    val password = mutableStateOf<String>("")
    val user = mutableStateOf(User())
    viewModel.isUser(user.value).collectAsState(false).let {
        if(it.value){
            println("I'll navigate")
        }else{
            println("not working")
        }
    }
    Surface(
        modifier = Modifier
            .size(1000.dp, 700.dp)
            .padding(5.dp)
            .shadow(3.dp, RoundedCornerShape(20.dp)),
        color = background,
        shape = RoundedCornerShape(20.dp) //window has round corners now
    ) {
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
                PasswordTextField("",
                    { passwordText -> password.value = passwordText },
                    errorMessage = "",
                    onNextChange = { print(it) },
                    modifier = Modifier.padding(vertical = 25.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    RoundedButton({
                        val newUser = User()
                        newUser.name = userName.value
                        newUser.password = password.value
                        user.value = newUser
                    }, "تسجيل الدخول")
                    OutlineRoundedButton({appClose.close()}, "خروج")
                }
            }
            RoundedImage("images/login-image.png", modifier = Modifier.padding(horizontal = 35.dp))
        }
    }
}


@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
