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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.*
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
    width: Dp = 0.dp,
    inputType: InputType = InputType.TEXT,
    maxLength: Int = Int.MAX_VALUE

    ) {
    var textValue by remember { mutableStateOf(value) }
    if (valueState != null) {
        textValue = valueState.value
    }
    val keyboardType = when (inputType) {
        InputType.NUMBER -> KeyboardType.Number
        InputType.TEXT -> KeyboardType.Text
    }
    val visualTransformation = when (inputType) {
        InputType.NUMBER -> NumberInputTransformation()
        InputType.TEXT -> TextInputTransformation()
    }

    Column(modifier.padding(5.dp).sizeIn(maxWidth = width)) {
        OutlinedTextField(
            value = textValue,
                onValueChange = {
                    onValueChange(it)
                    textValue = it.take(maxLength)
                },
            shape= RoundedCornerShape(16.dp),
            textStyle = CairoTypography.body1 ,
            keyboardActions = KeyboardActions(onNext = {
                onNextChange(textValue)
            }),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = keyboardType,
            ),
            label = { Text(
                hint,
                style = CairoTypography.h4.copy(color = hintText)
            ) },
            singleLine = true,
            isError = isError,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = primary,
                unfocusedIndicatorColor = Color.Gray,
                disabledIndicatorColor = Color.Transparent
            ),
            visualTransformation = visualTransformation

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
enum class InputType {
    NUMBER, TEXT
}
class NumberInputTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val filtered = text.text.filter { it.isDigit() }
        return TransformedText(
            AnnotatedString(filtered),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return filtered.indices.find { filtered.substring(0, it + 1).length == offset } ?: filtered.length
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return text.text.substring(0, offset).count { it.isDigit() }
                }
            }
        )
    }
}

class TextInputTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val filtered = text.text.filter { it.isLetter() }
        return TransformedText(
            AnnotatedString(filtered),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return filtered.indices.find { filtered.substring(0, it + 1).length == offset } ?: filtered.length
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return text.text.substring(0, offset).count { it.isLetter() }
                }
            }
        )
    }
}
@Preview()
@Composable
fun PreviewOutlinedTextField() {
    CustomOutlinedTextField(
        errorMessage = "hello"
    )
}