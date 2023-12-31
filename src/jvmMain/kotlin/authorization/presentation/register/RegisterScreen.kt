package authorization.presentation.register

import AlertSystem.presentation.showErrorMessage
import AlertSystem.presentation.showSuccessMessage
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import authorization.domain.model.Jobs
import authorization.presentation.component.PasswordTextField
import authorization.presentation.component.UserNameTextField
import common.component.*
import org.koin.compose.koinInject
import navcontroller.NavController
import styles.CairoTypography
import styles.AppColors.background

@Composable
fun RegisterScreen(
    navController: NavController<String>,
    viewModel: RegisterViewModel = koinInject(),
) {
    var showDialog by remember { mutableStateOf(false) }
    val state = viewModel.state
    LaunchedEffect(key1 = true) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                RegisterViewModel.ValidationEvent.Success -> {
                    showDialog = true
                    navController.navigate(AuthScreen.LoginAuthScreen.name)
                }
            }
        }
    }
    Surface(
        modifier = Modifier
            .padding(5.dp)
            .shadow(3.dp, RoundedCornerShape(20.dp)),
        color = background,
        shape = RoundedCornerShape(20.dp) //window has round corners now
    ) {

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
            ) {
                Image(
                    painter = painterResource("images/welcome.svg"),
                    contentDescription = "welcome.png",
                    modifier = Modifier.size(350.dp, 170.dp)
                )
                Text(
                    "الرجاء إدخال إسم المستخدم و كلمة السر",
                    style = CairoTypography.h3,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold
                )
                LazyVerticalGrid(
                    modifier = Modifier.sizeIn(maxWidth = 400.dp, minHeight = 250.dp),
                    columns = GridCells.Fixed(1),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    item {
                        UserNameTextField(
                            "",
                            { name -> viewModel.onEvent(RegistrationFormEvent.UsernameChanged(name)) },
                            isError = state.usernameError != null,
                            errorMessage = state.usernameError.toString(),
                            onNextChange = { print(it) })
                    }
//                    item {
//                        RoundedDropdownMenu(
//                            items = Jobs.values().toList(),
//                            onSelectItem = {},
//                            label = { Text(it.name) },
//                        ) {
//                            Text(it.name)
//                        }
//                    }
                    item {
                        PasswordTextField(
                            "",
                            { password -> viewModel.onEvent(RegistrationFormEvent.PasswordChanged(password)) },
                            isError = state.passwordError != null,
                            errorMessage = state.passwordError.toString(),
                            onNextChange = { print(it) },
                            modifier = Modifier.padding(vertical = 25.dp)
                        )
                    }
                    item {
                        PasswordTextField(
                            "",
                            { repeatedPassword ->
                                viewModel.onEvent(
                                    RegistrationFormEvent.RepeatedPasswordChanged(
                                        repeatedPassword
                                    )
                                )
                            },
                            isError = state.repeatedPasswordError != null,
                            errorMessage = state.repeatedPasswordError.toString(),
                            hint = "تكرار كلمة السر",
                            onNextChange = { print(it) },
                            modifier = Modifier.padding(vertical = 25.dp)
                        )
                    }
                    item {

                        Row(
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            RoundedButton({
                                viewModel.onEvent(RegistrationFormEvent.AcceptTerms(true))
                                viewModel.onEvent(RegistrationFormEvent.Submit)

                            }, "إنشاء حساب")
                            OutlineRoundedButton({ viewModel.closeApp() }, "خروج")
                        }
                    }
                }
            }
            RoundedImage("images/cover.jpg", modifier = Modifier.padding(horizontal = 35.dp))
        }
    }
    }
}




@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        NavController(AuthScreen.LoginAuthScreen.name)
    )
}
