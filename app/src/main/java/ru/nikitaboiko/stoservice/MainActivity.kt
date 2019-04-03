package ru.nikitaboiko.stoservice

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.nikitaboiko.stoservice.fragments.UserList
import ru.nikitaboiko.stoservice.fragments.UserListDialog
import ru.nikitaboiko.stoservice.fragments.UserLoginDialog
import ru.nikitaboiko.stoservice.fragments.UserRegDialog

class MainActivity : AppCompatActivity(), UserLoginDialog.OnFragmentInteractionListener,
    UserListDialog.OnFragmentInteractionListener {
    var fragment = UserList()

    override fun onFragmentInteraction(nextActivity: String, user: String) {
        Toast.makeText(this, "onFragmentInteraction $nextActivity $user", Toast.LENGTH_LONG).show()
        when (nextActivity) {
            "UserLogin" -> {
                val manager = supportFragmentManager
                val myDialogFragment = UserLoginDialog()
                val bundle = Bundle()
                bundle.putString("user", user)
                myDialogFragment.arguments = bundle
                myDialogFragment.show(manager, "dialog")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userRegButton = findViewById<View>(R.id.activity_main_button_user_reg)
        val addService = findViewById<View>(R.id.activity_main_button_add_service)
        val serviceList = findViewById<View>(R.id.activity_main_button_services_list)
        val userList = findViewById<View>(R.id.activity_main_button_user_list)

        userRegButton.setOnClickListener {
            val manager = supportFragmentManager
            val myDialogFragment = UserRegDialog()
            myDialogFragment.show(manager, "dialog")

            /*var fragment = UserRegFragment()
            var fTransaction = supportFragmentManager.beginTransaction()
            fTransaction.add(R.id.activity_main, fragment)
            fTransaction.commit()*/


        }

        userList.setOnClickListener {

            val manager = supportFragmentManager
            val myDialogFragment = UserListDialog()
            myDialogFragment.show(manager, "dialog")

            /*var fTransaction = supportFragmentManager.beginTransaction()
            fTransaction.add(R.id.activity_main, fragment)
            fTransaction.commit()
            var lm = RelativeLayout.LayoutParams(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
            fragment.view?.layoutParams = lm*/

            //fragment.view.layoutParams = lm



        }
    }

    override fun onPause() {
        var fTransaction = supportFragmentManager.beginTransaction()
        fTransaction.remove(fragment)
        fTransaction.commit()
        super.onPause()
    }
}
