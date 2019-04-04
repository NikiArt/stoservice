package ru.nikitaboiko.stoservice

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.nikitaboiko.stoservice.fragments.DateDialog
import ru.nikitaboiko.stoservice.fragments.ServiceAddDialog
import ru.nikitaboiko.stoservice.structure.Helpers
import java.util.*

class WorkerActivity : AppCompatActivity(), DateDialog.OnFragmentInteractionListener,
    ServiceAddDialog.OnFragmentInteractionListener {
    private lateinit var priceDate: TextView
    private lateinit var amount: TextView
    private lateinit var salary: TextView
    private lateinit var totalSalary: TextView
    private lateinit var user: String

    override fun onFragmentInteraction(nextActivity: String, unit: String) {
        when (nextActivity) {
            "SetDate" -> {
                priceDate.text = unit
                updateAmounts()
            }
            "UpdateServices" -> {

            }
        }
    }

    private fun updateAmounts() {
        val currentAmount =
            App.instance().dataControl.getTotalAmount(user, Helpers().getDatebyString(priceDate.text.toString())) * 0.4
        val currentSalary =
            App.instance().dataControl.getTotalSalary(user, Helpers().getDatebyString(priceDate.text.toString()))

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

        val activityLabel = findViewById<View>(R.id.activity_worker_label_user) as TextView
        val addWorkButton = findViewById<View>(R.id.activity_worker_button_addwork)
        val workList = findViewById<View>(R.id.activity_worker_list)
        amount = findViewById<View>(R.id.activity_worker_price_org) as TextView
        salary = findViewById<View>(R.id.activity_worker_price_worker) as TextView
        priceDate = findViewById<View>(R.id.activity_worker_date_price) as TextView
        totalSalary = findViewById<View>(R.id.activity_worker_total_salary) as TextView
        activityLabel.text = "Рабочее место сотрудника: $user"

        priceDate.text = Helpers().getStringbyDate(Date(), "01 MMMM y")
        priceDate.setOnClickListener {
            openDateDialog()
        }

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

    private fun openDateDialog() {
        val manager = supportFragmentManager
        val myDialogFragment = DateDialog()
        val bundle = Bundle()
        bundle.putString("startDate", priceDate.text.toString())
        myDialogFragment.arguments = bundle
        myDialogFragment.show(manager, "dialog")
    }
}