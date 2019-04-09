package ru.nikitaboiko.stoservice.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.nikitaboiko.stoservice.App
import ru.nikitaboiko.stoservice.structure.Helpers

class DeleteListDialog : DialogFragment() {
    val helpClass = Helpers.instance
    private lateinit var listener: OnFragmentInteractionListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bundle = arguments
        val title = bundle?.getString("title") ?: ""
        val message = bundle?.getString("message") ?: ""
        val button1String = "Удалить"
        val button2String = "Отмена"

        listener = context as OnFragmentInteractionListener

        val builder = AlertDialog.Builder(context!!)
        builder.setTitle(title)  // заголовок
        builder.setMessage(message) // сообщение
        builder.setPositiveButton(button1String) { dialog, id ->
            when (bundle?.getString("listType")) {
                "userList" -> {
                    App.instance.dataControl.deleteUser(helpClass.userList[bundle?.getInt("Id") ?: 0])
                }
                else -> {
                    App.instance.dataControl.deleteRecord(helpClass.record[bundle?.getInt("Id") ?: 0])
                }
            }
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
