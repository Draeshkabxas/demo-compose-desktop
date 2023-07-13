import androidx.compose.runtime.mutableStateOf
import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import utils.AgeGroup
import utils.Education
import utils.LibyanCities
import kotlin.random.Random

class FakePersonRepository : PersonRepository {
    private val persons: MutableList<Person> = mutableListOf()

    init {
        generatePersons()
    }


    private fun generatePersons() {
        val names = listOf("John", "Emma", "Michael", "Sophia", "William", "Olivia", "James", "Ava")
        val ageGroups = AgeGroup.values()
        var justificationsRequiredInputsNameAndValue = mapOf(
            "ملف" to mutableStateOf(false),
            "السيرة الذاتية" to mutableStateOf(false),
            "عدد 8 صور " to mutableStateOf(false),
            "شهادة الخلو من السوابق الجنائية" to mutableStateOf(false),
            "شهادة الوضع العائلي" to mutableStateOf(false),
            "افادة بعدم الارتباط بعمل" to mutableStateOf(false),
            "شهادة بعدم الجواز بأجنبية" to mutableStateOf(false),
            "شهادة بالاقامة من المجلس المحلي" to mutableStateOf(false),
            "تصوير كتيب العائلة بالكامل" to mutableStateOf(false),
            "المؤهل العلمي (افادة + كشف درجات الاصلي ) معتمد" to mutableStateOf(false),
            "طلب كتابي" to mutableStateOf(false),
            "موافقة ولي الامر" to mutableStateOf(false),
            "شهادة الجنسية" to mutableStateOf(false),
            " شهادة الدرن" to mutableStateOf(false),
            "الرقم الوطني" to mutableStateOf(false)
        )
        var proceduresInputNameAndValues = mapOf(
            "تحاليل" to mutableStateOf(false),
            "كشف طبي" to mutableStateOf(false),
            "لائق صحيا" to mutableStateOf(false),
            "غير لائق صحيا" to mutableStateOf(false),
            "مقابلة شخصية" to mutableStateOf(false),
            "إحالة لتدريب" to mutableStateOf(false),
        )
        for (i in 1..8) {
            val randomName = names.random()
            val randomAgeGroup = ageGroups.random()

            val person = Person(
                id = "ID$i",
                name = names.random(),
                motherName = names.random(),
                fileNumber = Random(5).nextDouble(100000.0).toString(),
                libyaId = Random(5).nextDouble(100000.0).toString(),
                phoneNumber = Random(5).nextDouble(100000.0).toString(),
                educationLevel = Education.values().random().name,
                recruiter = names.random(),
                city = LibyanCities.values().random().name,
                ageGroup = randomAgeGroup,
                justificationsRequire =justificationsRequiredInputsNameAndValue.map { it.key to (i % 2 == 0)  }.toMap(),
                procedures =  proceduresInputNameAndValues.map { it.key to (i % 2 == 0) }.toMap()
            )
            persons.add(person)
        }

    }

    override fun addPerson(person: Person): Flow<Boolean> {
        persons.add(person)
        return flowOf(true)
    }

    override fun getAllPeople(filterQuery: String): Flow<List<Person>> {
        val filteredPersons = persons.filter { it.name.contains(filterQuery, ignoreCase = true) }
        return flowOf(filteredPersons)
    }

    override fun getPerson(id: String): Flow<Person?> {
        val person = persons.find { it.id == id }
        return flowOf(person)
    }

    override fun updatePerson(person: Person): Flow<Boolean> {
        val index = persons.indexOfFirst { it.id == person.id }
        if (index != -1) {
            persons[index] = person
            return flowOf(true)
        }
        return flowOf(false)
    }
}
