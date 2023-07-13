import features.sons_of_officers.domain.model.Person
import features.sons_of_officers.domain.repository.PersonRepository
import features.sons_of_officers.domain.usecases.GetAllPeople
import features.sons_of_officers.presentation.sons_of_officers.FilterState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.junit.Test

class GetAllPeopleTest {

    lateinit var getAllPeople: GetAllPeople
    init {
        getAllPeople = GetAllPeople(FakePersonRepository())
    }


    @Test
    fun `Length of 'Hello, World!' should be 13`() {
        val str = "Hello, World!"
        assertEquals(13, str.length)
    }

    @Test
     fun checkHealthStatusTrue(){
         CoroutineScope(Dispatchers.Unconfined).launch {
             getAllPeople.invoke(FilterState(healthStatus = "لائق صحيا")).collectLatest {
                 it.data?.let {data->
                     assertEquals(4,data.size)
                 }
             }
         }
    }
    @Test
    suspend fun checkHealthStatusFalse(){
        CoroutineScope(Dispatchers.Unconfined).launch {
            getAllPeople.invoke(FilterState(healthStatus = "غير لائق صحيا")).collectLatest {
                it.data?.let { data ->
                    assertEquals(4, data.size)
                }
            }
        }
    }



}