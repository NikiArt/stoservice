package ru.nikitaboiko.stoservice

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nikitaboiko.stoservice.fragments.DeleteListDialog
import ru.nikitaboiko.stoservice.fragments.SalaryAddDialog
import ru.nikitaboiko.stoservice.fragments.adapter.AdminSalaryAdapter
import ru.nikitaboiko.stoservice.structure.Helpers

class AdminSalaryList : AppCompatActivity(), AdminSalaryAdapter.OnFragmentInteractionListener,
    DeleteListDialog.OnFragmentInteractionListener {
    private lateinit var salaryList: RecyclerView
    private lateinit var buttonAdd: Button
    private val salaryAdapter = AdminSalaryAdapter()
    private val helpClass = Helpers.instance

    override fun onFragmentInteraction(currentActivity: String, unit: String) {
        when (currentActivity) {
            "deleteSalary" -> {
                val manager = supportFragmentManager
                val myDialogFragment = DeleteListDialog()
                val bundle = Bundle()
                bundle.putInt("Id", unit.toInt())
                bundle.putString("title", "Удаление записи")
                bundle.putString(
                    "message",
                    "Вы действительно хотите удалить выдачу ${helpClass.salaryList[unit.toInt()].price}\u20BD сотруднику ${helpClass.salaryList[unit.toInt()].user}?"
                )
                bundle.putString("listType", "salaryList")
                myDialogFragment.arguments = bundle
                myDialogFragment.show(manager, "dialog")
            }
            "updateList" -> {
                updateSalaryList()
            }
        }
    }

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

        salaryAdapter.setLongClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        updateSalaryList()
    }

    fun initSalaryList() {
        val lm = LinearLayoutManager(this)
        salaryList.layoutManager = lm
        salaryList.adapter = salaryAdapter
    }


    private fun updateSalaryList() {
        App.instance.dataControl.getSalaries()
        salaryAdapter.update()
        salaryList.smoothScrollToPosition(if (helpClass.salaryList.isEmpty()) 0 else (helpClass.salaryList.size - 1))
    }
}
