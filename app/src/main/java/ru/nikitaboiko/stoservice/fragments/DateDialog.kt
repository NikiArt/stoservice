package ru.nikitaboiko.stoservice.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import ru.nikitaboiko.stoservice.structure.Helpers
import java.text.SimpleDateFormat
import java.util.*


class DateDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {
    lateinit var startDate: Date
    lateinit var periodVal: String
    val sdf = SimpleDateFormat("dd MMMM y", Locale.getDefault())
    private lateinit var listener: OnFragmentInteractionListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bundle = arguments
        val incomingDate = bundle?.getString("startDate") ?: ""
        val maxDate = bundle?.getString("maxDate") ?: ""
        val minDate = bundle?.getString("minDate") ?: ""
        periodVal = bundle?.getString("periodVal") ?: ""
        listener = context as OnFragmentInteractionListener
        startDate = sdf.parse(incomingDate)
        val c = Calendar.getInstance()
        c.time = startDate
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(getActivity(), this, year, month, day)
        if (!maxDate.isEmpty()) {
            val c = Calendar.getInstance()
            c.time = Helpers.instance.getDatebyString(maxDate)
            c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1)
            dpd.datePicker.maxDate = c.time.time - 1000
        }
        if (!minDate.isEmpty()) {
            val c = Calendar.getInstance()
            c.time = Helpers.instance.getDatebyString(minDate)
            dpd.datePicker.minDate = c.time.time
        }
        return dpd
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val c = Calendar.getInstance()
        c.set(year, month, day)

        val sdf = SimpleDateFormat("dd MMMM y", Locale.getDefault())
        val formattedDate = sdf.format(c.getTime())
        listener.onFragmentInteraction("${periodVal}SetDate", formattedDate)
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(nextActivity: String, unit: String)
    }
}