package ru.nikitaboiko.stoservice

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.nikitaboiko.stoservice.fragments.DeleteListDialog
import ru.nikitaboiko.stoservice.fragments.RecordAddDialog
import ru.nikitaboiko.stoservice.fragments.adapter.RecordServiceAdapter
import ru.nikitaboiko.stoservice.structure.Helpers
import java.text.SimpleDateFormat
import java.util.*


class RecordOnRepair : AppCompatActivity(), RecordServiceAdapter.OnFragmentInteractionListener,
    DeleteListDialog.OnFragmentInteractionListener {
    private lateinit var calendar: CalendarView
    private lateinit var fab: FloatingActionButton
    private lateinit var activityLabel: TextView
    private lateinit var recordsList: RecyclerView
    private val recordsAdapter = RecordServiceAdapter()
    private val helpClass = Helpers.instance
    private var calendarDate = helpClass.getDatebyString(helpClass.getStringbyDate(Date()))

    override fun onFragmentInteraction(currentActivity: String, unit: String) {
        when (currentActivity) {
            "deleteRecord" -> {
                val manager = supportFragmentManager
                val myDialogFragment = DeleteListDialog()
                val bundle = Bundle()
                bundle.putInt("Id", unit.toInt())
                bundle.putString("title", "Удаление записи")
                bundle.putString(
                    "message",
                    "Вы действительно хотите удалить запись на ${helpClass.record[unit.toInt()].date}?"
                )
                bundle.putString("listType", "recordList")
                myDialogFragment.arguments = bundle
                myDialogFragment.show(manager, "dialog")
            }
            "updateList" -> {
                updateRecordList(calendarDate)
            }
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
        recordsAdapter.setLongClickListener(this)
        fab.setOnClickListener {
            val intent = Intent(this, RecordAddDialog::class.java)
            intent.putExtra("date", helpClass.getStringbyDate(calendarDate))
            startActivity(intent)
        }

        val dp = resources.displayMetrics
        recordsList.layoutParams.height = (dp.heightPixels - (dp.density * 400)).toInt()
        calendar.layoutParams.height = (dp.density * 280).toInt()

        initServiceList()
        updateRecordList(helpClass.getDatebyString(helpClass.getStringbyDate(Date())))
        calendar.date = Date().time
        calendar.setOnDateChangeListener { calendarView: CalendarView, year: Int, month: Int, dateOfMonth: Int ->
            val dataFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            calendarDate = dataFormat.parse("$dateOfMonth.${month + 1}.$year")
            updateRecordList(calendarDate)
            activityLabel.text = "Запись на ремонт ${Helpers.instance.getStringbyDate(calendarDate, "dd MMMM y")}"
        }
    }

    override fun onResume() {
        super.onResume()
        updateRecordList(calendarDate)
    }

    fun initServiceList() {
        val lm = LinearLayoutManager(this)
        //lm.stackFromEnd = true
        recordsList.layoutManager = lm
        recordsList.adapter = recordsAdapter
    }


    private fun updateRecordList(date: Date) {
        App.instance.dataControl.getRecords(date)
        recordsAdapter.update()
        recordsList.smoothScrollToPosition(if (helpClass.record.isEmpty()) 0 else (helpClass.record.size - 1))
    }
}
