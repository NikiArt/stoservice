package ru.nikitaboiko.stoservice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import ru.nikitaboiko.stoservice.fragments.UserListDialog
import ru.nikitaboiko.stoservice.fragments.UserLoginDialog
import ru.nikitaboiko.stoservice.fragments.UserRegDialog
import java.util.*


class MainActivity : AppCompatActivity(), UserLoginDialog.OnFragmentInteractionListener,
    UserListDialog.OnFragmentInteractionListener, UserRegDialog.OnFragmentInteractionListener {

    override fun onFragmentInteraction(nextActivity: String, unit: String) {
        when (nextActivity) {
            "UserLogin" -> {
                val manager = supportFragmentManager
                val myDialogFragment = UserLoginDialog()
                val bundle = Bundle()
                bundle.putString("user", unit)
                myDialogFragment.arguments = bundle
                myDialogFragment.show(manager, "dialog")
            }
            "Workflow" -> {
                val intent = Intent(this, WorkerActivity::class.java)
                intent.putExtra("user", unit)
                startActivity(intent)
            }
            "AdminPanel" -> {
                val intent = Intent(this, AdminActivity::class.java)
                intent.putExtra("user", unit)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userList = findViewById<View>(R.id.activity_main_button_user_list)
        val records = findViewById<View>(R.id.activity_main_button_records)
        val admin = findViewById<View>(R.id.activity_main_button_admin)
        val button = findViewById<View>(R.id.button)
        button.setOnClickListener {
            testFirebase()
        }
        checkAdmin()

        userList.setOnClickListener {
            val manager = supportFragmentManager
            val myDialogFragment = UserListDialog()
            myDialogFragment.show(manager, "dialog")
        }

        records.setOnClickListener {
            val intent = Intent(this, RecordOnRepair::class.java)
            startActivity(intent)
        }

        admin.setOnClickListener {
            if (checkAdmin()) {
                val manager = supportFragmentManager
                val myDialogFragment = UserLoginDialog()
                val bundle = Bundle()
                bundle.putString("user", "Администратор")
                myDialogFragment.arguments = bundle
                myDialogFragment.show(manager, "dialog")
            }
        }
    }

    private fun checkAdmin(): Boolean {
        if (App.instance.dataControl.findUserId("Администратор") == null) {
            val manager = supportFragmentManager
            val myDialogFragment = UserRegDialog()
            val bundle = Bundle()
            bundle.putString("username", "Администратор")
            myDialogFragment.arguments = bundle
            myDialogFragment.show(manager, "dialog")
            return false
        } else {
            return true
        }
    }

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
