package ru.nikitaboiko.stoservice

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nikitaboiko.stoservice.fragments.adapter.AdminOrderAdapter
import ru.nikitaboiko.stoservice.structure.Helpers

class AdminOrdersList : AppCompatActivity() {
    private lateinit var salaryList: RecyclerView
    private val orderAdapter = AdminOrderAdapter()
    private val helpClass = Helpers.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_orders_list)
        salaryList = findViewById<View>(R.id.activity_admin_orders_list_list) as RecyclerView

        initSalaryList()
    }

    fun initSalaryList() {
        App.instance.dataControl.getServices()
        val lm = LinearLayoutManager(this)
        salaryList.layoutManager = lm
        salaryList.adapter = orderAdapter
    }
}
