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
    private lateinit var priceDateStart: TextView
    private lateinit var priceDateEnd: TextView
    private lateinit var amount: TextView
    private lateinit var salary: TextView
    private lateinit var totalSalary: TextView
    private lateinit var user: String
    lateinit var serviceList: RecyclerView
    val servicesAdapter = UserServiceAdapter()
    val helpClass = Helpers.instance

    override fun onFragmentInteraction(nextActivity: String, unit: String) {
        when (nextActivity) {
            "startSetDate" -> {
                priceDateStart.text = unit
                updateAmounts()
            }
            "endSetDate" -> {
                priceDateEnd.text = unit
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
                Helpers.instance.getDatebyString(priceDateStart.text.toString()),
                Helpers.instance.getDatebyString(priceDateEnd.text.toString()),
                true
            ) * 0.4
        currentAmount = Math.rint(100.0 * currentAmount) / 100.0
        var currentSalary =
            App.instance().dataControl.getTotalSalary(
                user,
                Helpers.instance.getDatebyString(priceDateStart.text.toString()),
                Helpers.instance.getDatebyString(priceDateEnd.text.toString())
            )
        currentSalary = Math.rint(100.0 * currentSalary) / 100.0

        amount.text = "Заработано за период: $currentAmount \u20BD"
        salary.text = "Получено за период: $currentSalary \u20BD"
        totalSalary.text = "${(Math.rint(100.0 * (currentAmount - currentSalary)) / 100.0)} \u20BD"
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
        priceDateStart = findViewById<View>(R.id.activity_worker_date_price_start) as TextView
        priceDateEnd = findViewById<View>(R.id.activity_worker_date_price_end) as TextView
        totalSalary = findViewById<View>(R.id.activity_worker_total_salary) as TextView
        serviceList = findViewById<View>(R.id.activity_worker_list) as RecyclerView
        val footer = findViewById<View>(R.id.activity_worker_footer)

        activityLabel.text = "Рабочее место сотрудника: $user"


        val dp = resources.displayMetrics
        serviceList.layoutParams.height = (dp.heightPixels - (dp.density * 420)).toInt()

        priceDateStart.text = Helpers().getStringbyDate(Date(), "01 MMMM y")
        priceDateStart.setOnClickListener {
            openDateDialog("start")
        }

        priceDateEnd.text = Helpers().getStringbyDate(Date(), "dd MMMM y")
        priceDateEnd.setOnClickListener {
            openDateDialog("end")
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
        serviceList.layoutManager = lm
        serviceList.adapter = servicesAdapter
        serviceList.smoothScrollToPosition(if (helpClass.servicesList.isEmpty()) 0 else (helpClass.servicesList.size - 1))

    }

    private fun openDateDialog(periodVal: String) {
        val manager = supportFragmentManager
        val myDialogFragment = DateDialog()
        val bundle = Bundle()
        bundle.putString("maxDate", helpClass.getStringbyDate(Date()))
        if (periodVal.equals("start")) {
            bundle.putString("startDate", priceDateStart.text.toString())
            if (helpClass.getDatebyString(priceDateEnd.text.toString()) < helpClass.getDatebyString(
                    helpClass.getStringbyDate(
                        Date()
                    )
                )
            ) {
                bundle.putString("maxDate", priceDateEnd.text.toString())
            }
        } else {
            bundle.putString("startDate", priceDateEnd.text.toString())
            bundle.putString("minDate", priceDateStart.text.toString())
        }

        bundle.putString("periodVal", periodVal)
        myDialogFragment.arguments = bundle
        myDialogFragment.show(manager, "dialog")
    }
}
