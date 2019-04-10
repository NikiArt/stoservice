package ru.nikitaboiko.stoservice.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.nikitaboiko.stoservice.App
import ru.nikitaboiko.stoservice.R
import ru.nikitaboiko.stoservice.structure.Helpers
import ru.nikitaboiko.stoservice.structure.Record
import java.util.*

class RecordAddDialog : AppCompatActivity(), DateDialog.OnFragmentInteractionListener,
    TimeDialog.OnFragmentInteractionListener {

    lateinit var car: EditText
    lateinit var client: EditText
    lateinit var telephone: EditText
    lateinit var comment: EditText
    lateinit var dateRec: TextView
    lateinit var shadowDate: String

    override fun onFragmentInteraction(nextActivity: String, unit: String) {
        when (nextActivity) {
            "SetDate" -> {
                shadowDate = unit
                val manager = supportFragmentManager
                val myDialogFragment = TimeDialog()
                myDialogFragment.show(manager, "dialog")
            }
            "SetTime" -> {
                dateRec.text = "$shadowDate $unit"
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_record_add)
        val buttonAdd = findViewById<View>(R.id.fragment_record_add_button_ok)
        car = findViewById<View>(R.id.fragment_record_add_car) as EditText
        client = findViewById<View>(R.id.fragment_record_add_client) as EditText
        telephone = findViewById<View>(R.id.fragment_record_add_telephone) as EditText
        comment = findViewById<View>(R.id.fragment_record_add_comment) as EditText
        dateRec = findViewById<View>(R.id.fragment_record_add_date) as TextView

        dateRec.text = "${intent.extras.get("date")} 00:00"
        shadowDate = dateRec.text.toString()
        buttonAdd.setOnClickListener {
            addRecord()
        }

        dateRec.setOnClickListener {
            chooseDate()
        }
    }

    private fun chooseDate() {
        val manager = supportFragmentManager
        val myDialogFragment = DateDialog()
        val bundle = Bundle()
        bundle.putString("startDate", dateRec.text.toString())
        myDialogFragment.arguments = bundle
        myDialogFragment.show(manager, "dialog")
    }

    private fun addRecord() {
        if (car.text.toString().isEmpty()) {
            Toast.makeText(this, "Необходимо указать информацию по автомобилю", Toast.LENGTH_SHORT).show()
            return
        }

        val id = UUID.randomUUID().toString()
        val currentRecord = Record(
            id,
            car.text.toString(),
            Helpers.instance.getDatebyString(dateRec.text.toString(), "dd MMMM yyyy HH:mm"),
            client.text.toString(),
            telephone.text.toString(),
            "",
            comment.text.toString()
        )
        App.instance().dataControl.addRecord(currentRecord)
        Helpers.instance.record.add(currentRecord)
        finish()
    }
}