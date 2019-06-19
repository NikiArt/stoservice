package ru.nikitaboiko.stoservice

import android.util.Log
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.google.firebase.firestore.FirebaseFirestore
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("ru.nikitaboiko.stoservice", appContext.packageName)
    }

    /*Service(
    val id: String,
    val car: String,
    val service: String,
    val user: String,
    val price: Double,
    val date: Date,
    var done: Boolean = false
    )*/

    @Test
    fun testFirebase() {
        /*val dbRef = FirebaseDatabase.getInstance().reference
        val id = UUID.randomUUID().toString()
        val service = Service(id, "citroen", "djfhsdjkfksd", "random user ID", 3054.00, Date())
        dbRef.child("services").setValue(service)
            .addOnSuccessListener {
                Log.d("firebase", "Success")
            }
            .addOnFailureListener {
                Log.d("firebase", "Failure")
            }*/

        val db = FirebaseFirestore.getInstance()
        val user = HashMap<String, Any>()
        user["first"] = "Ada"
        user["last"] = "Lovelace"
        user["born"] = 1815

        db.collection("services")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    "DDLog",
                    "DocumentSnapshot added with ID: " + documentReference.id
                )
            }
            .addOnFailureListener { e -> Log.w("DDLog", "Error adding document", e) }

        db.collection("services")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        Log.d("DDLog", document.id + " => " + document.data)
                    }
                } else {
                    Log.w("DDLog", "Error getting documents.", task.exception)
                }
            }
    }
}
