package ru.nikitaboiko.stoservice.fragments

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_record_add.view.*
import ru.nikitaboiko.stoservice.App
import ru.nikitaboiko.stoservice.R
import ru.nikitaboiko.stoservice.structure.Helpers
import ru.nikitaboiko.stoservice.structure.Record
import java.util.*

class RecordAddDialog : DialogFragment() {
    lateinit var car: EditText
    lateinit var client: EditText
    lateinit var telephone: EditText
    lateinit var comment: EditText
    lateinit var dateRec: TextView

    private lateinit var listener: OnFragmentInteractionListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val cont = context ?: return super.onCreateDialog(savedInstanceState)
        val activ = activity ?: return super.onCreateDialog(savedInstanceState)

        if (cont is OnFragmentInteractionListener) {
            listener = cont
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
        val builder = AlertDialog.Builder(cont)
        val inflater = activ.layoutInflater
        val inflatedView = inflater.inflate(R.layout.fragment_record_add, null)
        builder.setView(inflatedView)
        val buttonAdd = inflatedView.fragment_record_add_button_ok
        car = inflatedView.fragment_record_add_car
        client = inflatedView.fragment_record_add_client
        telephone = inflatedView.fragment_record_add_telephone
        comment = inflatedView.fragment_record_add_comment
        dateRec = inflatedView.fragment_record_add_date

        val modDate = (Date().time + 1000 * 60 * 60)
        dateRec.text = Helpers.instance.getStringbyDate(Date(modDate), "dd MMMM y HH:00")
        buttonAdd.setOnClickListener {
            addService()
        }

        return builder.create()
    }

    private fun addService() {
        if (car.text.toString().isEmpty()) {
            Toast.makeText(context, "Необходимо указать информацию по автомобилю", Toast.LENGTH_SHORT).show()
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
        listener.onFragmentInteraction("UpdateRecords", "")
        dismiss()
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(currentActivity: String, unit: String)
    }
}