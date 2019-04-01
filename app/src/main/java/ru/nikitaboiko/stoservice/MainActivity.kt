package ru.nikitaboiko.stoservice

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.nikitaboiko.stoservice.fragments.UserRegFragment

class MainActivity : AppCompatActivity(), UserRegFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userRegButton = findViewById<View>(R.id.activity_main_button_user_reg)
        val addService = findViewById<View>(R.id.activity_main_button_add_service)
        val serviceList = findViewById<View>(R.id.activity_main_button_services_list)

        userRegButton.setOnClickListener {
            var fragment = UserRegFragment()
            var fTransaction = supportFragmentManager.beginTransaction()
            fTransaction.add(R.id.activity_main, fragment)
            fTransaction.commit()


        }


    }
}
