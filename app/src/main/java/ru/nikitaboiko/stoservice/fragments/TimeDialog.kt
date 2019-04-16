package ru.nikitaboiko.stoservice.fragments

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*


class TimeDialog : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private lateinit var listener: OnFragmentInteractionListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        listener = context as OnFragmentInteractionListener
        return TimePickerDialog(getActivity(), this, 0, 0, true)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        val c = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY, hourOfDay)
        c.set(Calendar.MINUTE, minute)

        val sdf = SimpleDateFormat(" HH:mm", Locale.getDefault())
        val formattedDate = sdf.format(c.getTime())
        listener.onFragmentInteraction("SetTime", formattedDate)
    }


    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(nextActivity: String, unit: String)
    }
}