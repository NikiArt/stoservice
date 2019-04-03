package ru.nikitaboiko.stoservice

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.nikitaboiko.stoservice.fragments.DateDialog
import java.text.SimpleDateFormat
import java.util.*

class WorkerActivity : AppCompatActivity() {
    lateinit var priceDate: TextView
    val sdf = SimpleDateFormat("01 MMMM y")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker)
        val displaymetrics = resources.displayMetrics
        val addWorkButton = findViewById<View>(R.id.activity_worker_button_addwork)
        priceDate = findViewById<View>(R.id.activity_worker_date_price) as TextView
        val workList = findViewById<View>(R.id.activity_worker_work_list)
        priceDate.text = sdf.format(Date())
        priceDate.setOnClickListener {
            openDateDialog()
        }
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
