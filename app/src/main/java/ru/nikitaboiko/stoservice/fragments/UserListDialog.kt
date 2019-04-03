package ru.nikitaboiko.stoservice.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.nikitaboiko.stoservice.App


class UserListDialog : DialogFragment() {
    private lateinit var listener: OnFragmentInteractionListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var users = App.instance().dataControl.getUserList().toTypedArray().sortedArray()
        val cont = context ?: return super.onCreateDialog(savedInstanceState)

        if (cont is OnFragmentInteractionListener) {
            listener = cont
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
        val adb = AlertDialog.Builder(cont)
        adb.setTitle("Выберите пользователя")
            .setItems(users, DialogInterface.OnClickListener { dialog, which ->
                listener.onFragmentInteraction("UserLogin", users[which])
            })
        return adb.create()
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(nextActivity: String, user: String)
    }
}