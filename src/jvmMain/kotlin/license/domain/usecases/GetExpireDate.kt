package license.domain.usecases

import license.domain.repository.SharedPreferencesRepository
import license.domain.utils.Keys
import java.util.Date

class GetExpireDate(
    private val sharedPref:SharedPreferencesRepository
) {
    operator fun invoke():Date{
        return sharedPref.getDate(Keys.ExpirationDate)
    }
}