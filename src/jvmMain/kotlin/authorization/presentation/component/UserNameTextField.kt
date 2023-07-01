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
import androidx.compose.ui.unit.dp
import styles.CairoTypography
import styles.AppColors.hintText
import styles.AppColors.primary

@Composable
fun UserNameTextField(
    value: String= "",
    onValueChange: (String) -> Unit = {},
    onNextChange: (String) -> Unit = {},
    hint:String ="إسم المستخدم",
    isError: Boolean = false,
    errorMessage: String,
    modifier:Modifier = Modifier
) {
    var textValue by remember { mutableStateOf(value) }

    Column(modifier) {
        TextField(
            value = textValue,
            onValueChange = {
                onValueChange(it)
                textValue = it
            },
            textStyle = CairoTypography.h2 ,
            keyboardActions = KeyboardActions(onNext = {
                onNextChange(textValue)
            }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            label = { Text(
                hint,
                style = CairoTypography.h3.copy(color = hintText)
            ) },
            trailingIcon = { Icon(modifier = Modifier.size(25.dp), painter =  painterResource("images/user.svg"), contentDescription = null) },
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

