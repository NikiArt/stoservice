package ru.nikitaboiko.stoservice.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*


class DateDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {
    lateinit var startDate: Date
    val sdf = SimpleDateFormat("dd MMMM y")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bundle = arguments
        val incomingDate = bundle?.getString("startDate") ?: ""
        startDate = sdf.parse(incomingDate)
        val c = Calendar.getInstance()
        c.time = startDate
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(getActivity(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val c = Calendar.getInstance()
        c.set(year, month, day)

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate = sdf.format(c.getTime())
    }
}