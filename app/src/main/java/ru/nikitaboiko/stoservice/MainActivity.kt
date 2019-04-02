package ru.nikitaboiko.stoservice

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import ru.nikitaboiko.stoservice.fragments.UserList
import ru.nikitaboiko.stoservice.fragments.UserRegFragment

class MainActivity : AppCompatActivity(), UserRegFragment.OnFragmentInteractionListener,
    UserList.OnFragmentInteractionListener {
    var fragment = UserList()

    override fun onFragmentInteraction(uri: Uri) {
    }

    override fun onCreateDialog(id: Int, args: Bundle?): Dialog? {
        return super.onCreateDialog(id, args)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userRegButton = findViewById<View>(R.id.activity_main_button_user_reg)
        val addService = findViewById<View>(R.id.activity_main_button_add_service)
        val serviceList = findViewById<View>(R.id.activity_main_button_services_list)
        val userList = findViewById<View>(R.id.activity_main_button_user_list)

        /* userRegButton.setOnClickListener {
             var fragment = UserRegFragment()
             var fTransaction = supportFragmentManager.beginTransaction()
             fTransaction.add(R.id.activity_main, fragment)
             fTransaction.commit()


         }*/

        userList.setOnClickListener {
            var fTransaction = supportFragmentManager.beginTransaction()
            fTransaction.add(R.id.activity_main, fragment)
            fTransaction.commit()
            var lm = RelativeLayout.LayoutParams(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
            fragment.view?.layoutParams = lm
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
