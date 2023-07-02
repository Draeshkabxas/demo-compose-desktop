package features.sons_of_officers.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import authorization.domain.model.ValidationResult

data class PersonalItemInfo(
    val name:String,
    val value:MutableState<String> = mutableStateOf(""),
    val validateMethod:(String,String) -> ValidationResult
){

    fun check():String{
        return validateMethod.invoke(name,value.value).errorMessage.toString()
    }
    override fun equals(other: Any?): Boolean {
        return (other as PersonalItemInfo).name == this.name
    }
}
