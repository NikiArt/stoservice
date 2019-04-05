package ru.nikitaboiko.stoservice

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nikitaboiko.stoservice.fragments.DateDialog
import ru.nikitaboiko.stoservice.fragments.ServiceAddDialog
import ru.nikitaboiko.stoservice.fragments.adapter.UserServiceAdapter
import ru.nikitaboiko.stoservice.structure.Helpers
import java.util.*

class WorkerActivity : AppCompatActivity(), DateDialog.OnFragmentInteractionListener,
    ServiceAddDialog.OnFragmentInteractionListener {
    private lateinit var priceDate: TextView
    private lateinit var amount: TextView
    private lateinit var salary: TextView
    private lateinit var totalSalary: TextView
    private lateinit var user: String
    lateinit var serviceList: RecyclerView
    val servicesAdapter = UserServiceAdapter()

    override fun onFragmentInteraction(nextActivity: String, unit: String) {
        when (nextActivity) {
            "SetDate" -> {
                priceDate.text = unit
                updateAmounts()
            }
            "UpdateServices" -> {
                updateAmounts()
                servicesAdapter.update()
                serviceList.scrollToPosition(Helpers.instance.servicesList.size - 1)
            }
        }
    }

    private fun updateAmounts() {

        var currentAmount =
            App.instance().dataControl.getTotalAmount(
                user,
                Helpers.instance.getDatebyString(priceDate.text.toString())
            ) * 0.4
        currentAmount = Math.rint(100.0 * currentAmount) / 100.0
        var currentSalary =
            App.instance().dataControl.getTotalSalary(user, Helpers.instance.getDatebyString(priceDate.text.toString()))
        currentSalary = Math.rint(100.0 * currentSalary) / 100.0

        amount.text = "Заработано за период: $currentAmount \u20BD"
        salary.text = "Получено за период: $currentSalary \u20BD"
        totalSalary.text =
            "${App.instance().dataControl.getTotalAmount(user) * 0.4 - App.instance().dataControl.getTotalSalary(user)}"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker)
        val args = intent.extras
        user = args.get("user") as String

        App.instance.dataControl.getServices(user)

        val activityLabel = findViewById<View>(R.id.activity_worker_label_user) as TextView
        val addWorkButton = findViewById<View>(R.id.activity_worker_button_addwork)
        amount = findViewById<View>(R.id.activity_worker_price_org) as TextView
        salary = findViewById<View>(R.id.activity_worker_price_worker) as TextView
        priceDate = findViewById<View>(R.id.activity_worker_date_price) as TextView
        totalSalary = findViewById<View>(R.id.activity_worker_total_salary) as TextView
        serviceList = findViewById<View>(R.id.activity_worker_list) as RecyclerView
        val footer = findViewById<View>(R.id.activity_worker_footer)

        activityLabel.text = "Рабочее место сотрудника: $user"


        val dp = resources.displayMetrics
        serviceList.layoutParams.height = (dp.heightPixels - (dp.density * 400)).toInt()

        priceDate.text = Helpers().getStringbyDate(Date(), "01 MMMM y")
        priceDate.setOnClickListener {
            openDateDialog()
        }

        initServiceList()

        addWorkButton.setOnClickListener {
            val manager = supportFragmentManager
            val myDialogFragment = ServiceAddDialog()
            val bundle = Bundle()
            bundle.putString("user", user)
            myDialogFragment.arguments = bundle
            myDialogFragment.show(manager, "dialog")
        }

        updateAmounts()
    }


    fun initServiceList() {
        val lm = LinearLayoutManager(this)
        lm.stackFromEnd = true
        serviceList.layoutManager = lm
        serviceList.adapter = servicesAdapter

    }

    private fun openDateDialog() {
        val manager = supportFragmentManager
        val myDialogFragment = DateDialog()
        val bundle = Bundle()
        bundle.putString("startDate", priceDate.text.toString())
        myDialogFragment.arguments = bundle
        myDialogFragment.show(manager, "dialog")
    }
}
