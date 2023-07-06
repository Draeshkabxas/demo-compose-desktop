package utils

import java.util.*

enum class AgeGroup(val arabicName:String) {
    UnderEightTeen("قل من 18"),
    BetweenEightTeenAndThirty("من 18 الى 30"),
    AboveThirty("أعلى من 30");
}

fun getAllAgeGroupArabicNames(): List<String> =
    AgeGroup.values().map { it.arabicName }

fun String.fromArabicNameToAgeGroup():AgeGroup =
    AgeGroup.values().first { it.arabicName.equals(this,ignoreCase = true)}

fun getAgeGroupFromLibyaId(libyaId: String): AgeGroup {
    if (libyaId.isEmpty()) return AgeGroup.UnderEightTeen
    val birthDate = libyaId.substring(1, 5).toInt()
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val age = currentYear - birthDate
    return when {
        age < 18 -> AgeGroup.UnderEightTeen
        age in 18..30 -> AgeGroup.BetweenEightTeenAndThirty
        else -> AgeGroup.AboveThirty
    }
}