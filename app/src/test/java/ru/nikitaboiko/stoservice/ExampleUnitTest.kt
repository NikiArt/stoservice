package ru.nikitaboiko.stoservice

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.nikitaboiko.stoservice.structure.Helpers
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testHelpers() {
        val text = "   dsfsd asdfsd dsf  "
        println(Helpers().delSpaces(text))
        println(Helpers().delSpaces(text, false))
    }

    @Test
    fun testDate() {
        val millis = 1554371159
        var date = Date()
        date.time = 1554371159
        println(date.toString())
    }
}
