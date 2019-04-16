package ru.nikitaboiko.stoservice

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nikitaboiko.stoservice.fragments.UserListDialog
import ru.nikitaboiko.stoservice.fragments.adapter.AdminOrderAdapter
import ru.nikitaboiko.stoservice.structure.Helpers

class AdminOrdersList : AppCompatActivity(), UserListDialog.OnFragmentInteractionListener {
    private lateinit var orderList: RecyclerView
    private lateinit var userSelection: EditText
    private lateinit var buttonClear: ImageButton
    private val orderAdapter = AdminOrderAdapter()
    private val helpClass = Helpers.instance

    override fun onFragmentInteraction(nextActivity: String, unit: String) {
        when (nextActivity) {
            "UserLogin" -> {
                userSelection.text.clear()
                userSelection.text.append(unit)
                App.instance.dataControl.getServices(unit)
                orderAdapter.update()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_orders_list)
        orderList = findViewById<View>(R.id.activity_admin_orders_list_list) as RecyclerView
        userSelection = findViewById<View>(R.id.activity_admin_orders_list_selection) as EditText
        userSelection.keyListener = null
        buttonClear = findViewById<View>(R.id.activity_admin_orders_list_clear_selection) as ImageButton

        buttonClear.setOnClickListener {
            userSelection.text.clear()
            App.instance.dataControl.getServices()
            orderAdapter.update()
        }

        userSelection.setOnClickListener {
            val manager = supportFragmentManager
            val myDialogFragment = UserListDialog()
            myDialogFragment.show(manager, "dialog")
        }

        initSalaryList()
    }

    fun initSalaryList() {
        App.instance.dataControl.getServices()
        val lm = LinearLayoutManager(this)
        orderList.layoutManager = lm
        orderList.adapter = orderAdapter
    }
}
