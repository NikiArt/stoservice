package ru.nikitaboiko.stoservice.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.nikitaboiko.stoservice.App
import ru.nikitaboiko.stoservice.structure.Helpers
import ru.nikitaboiko.stoservice.structure.Record

class RecordDeleteDialog : DialogFragment() {
    val helpClass = Helpers.instance
    lateinit var record: Record
    private lateinit var listener: OnFragmentInteractionListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bundle = arguments
        record = helpClass.record[bundle?.getInt("recordId") ?: 0]
        val title = "Удаление записи"
        val message = "Вы действительно хотите удалить запись на ${record.date}?"
        val button1String = "Удалить"
        val button2String = "Отмена"
        listener = context as OnFragmentInteractionListener

        val builder = AlertDialog.Builder(context!!)
        builder.setTitle(title)  // заголовок
        builder.setMessage(message) // сообщение
        builder.setPositiveButton(button1String) { dialog, id ->
            App.instance.dataControl.deleteRecord(record)
            listener.onFragmentInteraction("updateList", 0)
        }
        builder.setNegativeButton(button2String) { dialog, id ->
            dismiss()
        }
        builder.setCancelable(true)
        return builder.create()
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(currentActivity: String, unit: Int)
    }
}
