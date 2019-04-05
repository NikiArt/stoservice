package ru.nikitaboiko.stoservice

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.nikitaboiko.stoservice.fragments.RecordAddDialog
import ru.nikitaboiko.stoservice.fragments.adapter.RecordServiceAdapter
import ru.nikitaboiko.stoservice.structure.Helpers
import java.text.SimpleDateFormat
import java.util.*


class RecordOnRepair : AppCompatActivity(), RecordAddDialog.OnFragmentInteractionListener {
    private lateinit var calendar: CalendarView
    private lateinit var fab: FloatingActionButton
    private lateinit var activityLabel: TextView
    private lateinit var recordsList: RecyclerView
    private val recordsAdapter = RecordServiceAdapter()

    override fun onFragmentInteraction(nextActivity: String, unit: String) {
        when (nextActivity) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_on_repair)
        activityLabel = findViewById<View>(R.id.activity_record_on_repair_label) as TextView
        calendar = findViewById<View>(R.id.activity_record_on_repair_calendar) as CalendarView
        recordsList = findViewById<View>(R.id.activity_record_on_repair_list) as RecyclerView
        fab = findViewById<View>(R.id.activity_record_on_repair_button_add) as FloatingActionButton
        activityLabel.text = "Запись на ремонт ${Helpers.instance.getStringbyDate(Date(), "dd MMMM y")}"
        fab.setOnClickListener {
            val manager = supportFragmentManager
            val myDialogFragment = RecordAddDialog()
            myDialogFragment.show(manager, "dialog")
        }
        initServiceList()
        updateRecordList(Date())
        calendar.date = Date().time
        calendar.setOnDateChangeListener { calendarView: CalendarView, year: Int, month: Int, dateOfMonth: Int ->
            val dataFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val date = dataFormat.parse("$dateOfMonth.${month + 1}.$year")
            updateRecordList(date)
            activityLabel.text = "Запись на ремонт ${Helpers.instance.getStringbyDate(date, "dd MMMM y")}"
        }
    }

    fun initServiceList() {
        val lm = LinearLayoutManager(this)
        lm.stackFromEnd = true
        recordsList.layoutManager = lm
        recordsList.adapter = recordsAdapter

    }

    private fun addRecord() {

    }

    private fun updateRecordList(date: Date) {
        App.instance.dataControl.getRecords(date)
        recordsAdapter.update()
    }
}
