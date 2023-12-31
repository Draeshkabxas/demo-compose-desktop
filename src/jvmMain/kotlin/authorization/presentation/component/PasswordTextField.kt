package authorization.presentation.component


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import styles.CairoTypography
import styles.AppColors.hintText
import styles.AppColors.primary

@Composable
fun PasswordTextField(
    value: String= "",
    onValueChange: (String) -> Unit = {},
    onNextChange: (String) -> Unit = {},
    hint:String ="كلمة السر",
    isError: Boolean = false,
    errorMessage: String,
    modifier:Modifier = Modifier
) {
    var textValue by remember { mutableStateOf(value) }
    var passwordVisibility by remember { mutableStateOf(false) }

    Column(modifier) {
        TextField(
            value = textValue,
            onValueChange = {
                onValueChange(it)
                textValue = it
            },
            textStyle = CairoTypography.h2 ,
            keyboardActions = KeyboardActions(onDone = {
                onNextChange(textValue)
            }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            label = { Text(
                hint,
                style = CairoTypography.h3.copy(color = hintText)
            ) },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = if (passwordVisibility) painterResource("images/visibility.svg") else painterResource("images/visibility_off.svg"),
                        contentDescription = if (passwordVisibility) "Hide password" else "Show password"
                    )
                }
            },
            singleLine = true,
            isError = isError,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = primary,
                unfocusedIndicatorColor = Color.Gray,
                disabledIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.padding(10.dp))
        if (isError) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = CairoTypography.h3
            )
        }
    }
}

