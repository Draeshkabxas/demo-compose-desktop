package license.data.repository

import license.domain.repository.SharedPreferencesRepository
import java.time.LocalDate
import java.util.*
import java.util.prefs.Preferences

class SharedPreferencesImpl:SharedPreferencesRepository {
    private val preferences = Preferences.userRoot().node("")

    override fun saveDate(key: String, date: Date) {
        preferences.putLong(key, date.time)
    }

    override fun getDate(key: String): Date {
        val date =preferences.getLong(key, 0L)
        if (date == 0L){
            saveDate(key, Date(System.currentTimeMillis()))
            getDate(key)
        }
        return Date(date)
    }
}