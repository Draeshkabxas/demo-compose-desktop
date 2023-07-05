package common.component

import androidx.compose.foundation.layout.padding
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import styles.CairoTypography
import styles.AppColors.hintText
import styles.AppColors.primary

@Composable
fun CustomOutlinedTextField(
    value: String= "",
    onValueChange: (String) -> Unit = {},
    onNextChange: (String) -> Unit = {},
    hint:String ="إسم المستخدم",
    isError: Boolean = false,
    errorMessage: String,
    valueState: MutableState<String>? =null,
    modifier:Modifier = Modifier,
    width: Dp = 0.dp
) {
    var textValue = remember { mutableStateOf(value) }
    if (valueState != null) {
        textValue = valueState
    }
    Column(modifier.padding(5.dp).sizeIn(maxWidth = width)) {
        OutlinedTextField(
            value = textValue.value,
            onValueChange = {
                onValueChange(it)
                textValue.value = it
            },
            shape= RoundedCornerShape(16.dp),
            textStyle = CairoTypography.body1 ,
            keyboardActions = KeyboardActions(onNext = {
                onNextChange(textValue.value)
            }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            label = { Text(
                hint,
                style = CairoTypography.body2.copy(color = hintText)
            ) },
            singleLine = true,
            isError = isError,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = primary,
                unfocusedIndicatorColor = Color.Gray,
                disabledIndicatorColor = Color.Transparent
            )
        )
        if (isError) {
            Text(
                modifier = Modifier.padding(vertical = 5.dp),
                text = errorMessage,
                color = Color.Red,
                style = CairoTypography.body2
            )
        }

    }
}

@Preview()
@Composable
fun PreviewOutlinedTextField() {
    CustomOutlinedTextField(
        errorMessage = "hello"
    )
}