package license.presentation

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import license.domain.usecases.ActivateLicense
import license.domain.usecases.GetExpireDate
import utils.Resource
import java.util.*

class AppLicenseViewModel(
    private val activateLicense: ActivateLicense,
    private val getExpireDate: GetExpireDate
) {


    val showAppLicense = mutableStateOf(true)
    val message = mutableStateOf("")
    val showOpenSystem = mutableStateOf(false)
    init {
        checkExpireDate()
    }

    private fun checkExpireDate(){
        val today = Date(System.currentTimeMillis())
        showAppLicense.value =  today > getExpireDate()
        println(today > getExpireDate())
    }

    fun openSystem(){
        checkExpireDate()
    }

    fun activateLicense(filePath:String){
        activateLicense.invoke(filePath).onEach {
            when(it){
                is Resource.Error -> {
                    message.value = it.message.toString()
                }
                is Resource.Loading -> {
                    message.value = "يرجى الانتظار يتم الان فك تشفير الملف"
                }
                is Resource.Success -> {
                    message.value = it.data.toString()
                    showOpenSystem.value = true
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }
}