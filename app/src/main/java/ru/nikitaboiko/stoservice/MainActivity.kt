package ru.nikitaboiko.stoservice

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.nikitaboiko.stoservice.fragments.UserListDialog
import ru.nikitaboiko.stoservice.fragments.UserLoginDialog
import ru.nikitaboiko.stoservice.fragments.UserRegDialog


class MainActivity : AppCompatActivity(), UserLoginDialog.OnFragmentInteractionListener,
    UserListDialog.OnFragmentInteractionListener {

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
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userRegButton = findViewById<View>(R.id.activity_main_button_user_reg)
        val userList = findViewById<View>(R.id.activity_main_button_user_list)
        val records = findViewById<View>(R.id.activity_main_button_records)
        val admin = findViewById<View>(R.id.activity_main_button_admin)
        userRegButton.setOnClickListener {
            val manager = supportFragmentManager
            val myDialogFragment = UserRegDialog()
            myDialogFragment.show(manager, "dialog")
        }

        userList.setOnClickListener {
            val manager = supportFragmentManager
            val myDialogFragment = UserListDialog()
            myDialogFragment.show(manager, "dialog")
        }

        records.setOnClickListener {
            val intent = Intent(this, RecordOnRepair::class.java)
            startActivity(intent)
        }
    }
}
