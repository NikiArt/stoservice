package ru.nikitaboiko.stoservice

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.nikitaboiko.stoservice.fragments.DateDialog
import ru.nikitaboiko.stoservice.structure.Helpers
import java.util.*

class WorkerActivity : AppCompatActivity(), DateDialog.OnFragmentInteractionListener {
    lateinit var priceDate: TextView
    lateinit var amount: TextView
    lateinit var sailary: TextView
    lateinit var user: String

    override fun onFragmentInteraction(nextActivity: String, unit: String) {
        when (nextActivity) {
            "SetDate" -> {
                priceDate.text = unit
                updateAmounts()
            }
        }
    }

    private fun updateAmounts() {
        amount.text = "Заработано за период: ${App.instance().dataControl.getTotalAmount(
            user,
            Helpers().getDatebyString(priceDate.text.toString())
        )} \u20BD"
        sailary.text = "Получено за период: ${App.instance().dataControl.getTotalSalary(
            user,
            Helpers().getDatebyString(priceDate.text.toString())
        )} \u20BD"
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
        sailary = findViewById<View>(R.id.activity_worker_price_worker) as TextView
        priceDate = findViewById<View>(R.id.activity_worker_date_price) as TextView
        activityLabel.text = "Рабочее место сотрудника: $user"

        priceDate.text = Helpers().getStringbyDate(Date(), "01 MMMM y")
        priceDate.setOnClickListener {
            openDateDialog()
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
