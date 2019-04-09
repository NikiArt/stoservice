package ru.nikitaboiko.stoservice

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        val salaryButton = findViewById<View>(R.id.activity_admin_button_salary)
        val worksButton = findViewById<View>(R.id.activity_admin_button_works)
        val usersButton = findViewById<View>(R.id.activity_admin_button_users)

        usersButton.setOnClickListener {
            val intent = Intent(this, AdminUserList::class.java)
            startActivity(intent)
        }
    }
}
