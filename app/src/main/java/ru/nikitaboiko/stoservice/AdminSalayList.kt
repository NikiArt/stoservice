package ru.nikitaboiko.stoservice

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nikitaboiko.stoservice.fragments.SalaryAddDialog
import ru.nikitaboiko.stoservice.fragments.adapter.AdminSalaryAdapter
import ru.nikitaboiko.stoservice.structure.Helpers

class AdminSalaryList : AppCompatActivity() {
    private lateinit var salaryList: RecyclerView
    private lateinit var buttonAdd: Button
    private val salaryAdapter = AdminSalaryAdapter()
    private val helpClass = Helpers.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_salay_list)
        salaryList = findViewById<View>(R.id.activity_admin_salary_list_list) as RecyclerView
        buttonAdd = findViewById<View>(R.id.activity_admin_salary_list_add) as Button

        initSalaryList()
        buttonAdd.setOnClickListener {
            val intent = Intent(this, SalaryAddDialog::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        updateRecordList()
    }

    fun initSalaryList() {
        val lm = LinearLayoutManager(this)
        salaryList.layoutManager = lm
        salaryList.adapter = salaryAdapter
    }


    private fun updateRecordList() {
        App.instance.dataControl.getSalaries()
        salaryAdapter.update()
        salaryList.smoothScrollToPosition(if (helpClass.salaryList.isEmpty()) 0 else (helpClass.salaryList.size - 1))
    }
}
