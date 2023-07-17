package utils

import utils.HealthStatus.All

enum class HealthStatus(val arabicName: String) {
    INAPPROPRIATE("غير لائق"),
    APPROPRIATE("لائق"),
    All("الجميع")
}

fun getHealthStatusFromArabicName(arabicName: String):HealthStatus{
    return HealthStatus.values().find { it.arabicName == arabicName } ?: All
}
