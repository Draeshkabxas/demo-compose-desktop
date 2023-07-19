package license.domain.repository

import java.util.Date

interface SharedPreferencesRepository {
    fun saveDate(key: String, date: Date)
    fun getDate(key: String): Date
}