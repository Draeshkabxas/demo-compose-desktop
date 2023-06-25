package authorization.presentation.component


import androidx.compose.foundation.layout.Column
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
import styles.Colors.hintText
import styles.Colors.primary

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
    var isErrorState by remember { mutableStateOf(isError) }

    Column(modifier) {
        TextField(
            value = textValue,
            onValueChange = {
                onValueChange(it)
                isErrorState = textValue.isEmpty() || textValue.length < 8 // You can replace this with your own validation logic
                textValue = it
            },
            textStyle = CairoTypography.h2 ,
            keyboardActions = KeyboardActions(onDone = {
                onNextChange(textValue)
                isErrorState = textValue.isEmpty() || textValue.length < 8 // You can replace this with your own validation logic
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
            isError = isErrorState,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = primary,
                unfocusedIndicatorColor = Color.Gray,
                disabledIndicatorColor = Color.Transparent
            )
        )
        if (isErrorState) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.caption
            )
        }
    }
}

