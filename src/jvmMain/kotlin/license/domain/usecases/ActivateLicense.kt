package license.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import license.domain.model.License
import license.domain.repository.LicenseRepository
import license.domain.repository.SharedPreferencesRepository
import license.domain.utils.Keys
import utils.Resource
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class ActivateLicense(
    private val licenseRepo:LicenseRepository,
    private val sharedPref:SharedPreferencesRepository
) {
        operator fun invoke(filePath:String): Flow<Resource<String>> = flow{
            emit(Resource.Loading(data = ""))
            val license =licenseRepo.getLicense(filePath).first()
            var resultMessage = "لا يوجد مفتاح تشغيل في هذا الملف"
            when(license){
                License.Lifetime -> {
                    resultMessage = "تفعيل المنظومة لمدى الحياة"
                    activateLifetime()
                }
                License.TenDay -> {
                    resultMessage = "تفعيل المنظومة عشرة أيام"
                    activateTenDays()
                }
                License.None -> {
                    emit(Resource.Error(message = resultMessage))
                }
            }
            emit(Resource.Success(data = resultMessage))
        }.catch { emit(Resource.Error(message = "لا يوجد مفتاح تشغيل في هذا الملف")) }

    private fun activateTenDays() {
        val currentDate = LocalDate.now()
        val newDate = currentDate.plusDays(10)
        val zoneId = ZoneId.systemDefault()
        val expireDate = Date.from(newDate.atStartOfDay(zoneId).toInstant())
        sharedPref.saveDate(Keys.ExpirationDate,expireDate)
    }

    private fun activateLifetime() {
        val currentDate = LocalDate.now()
        val newDate = currentDate.plusYears(300)
        val zoneId = ZoneId.systemDefault()
        val expireDate = Date.from(newDate.atStartOfDay(zoneId).toInstant())
        sharedPref.saveDate(Keys.ExpirationDate,expireDate);
    }
}