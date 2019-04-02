package ru.nikitaboiko.stoservice.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.nikitaboiko.stoservice.App


class UserListDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var users = App.instance().dataControl.getUserList().toTypedArray()
        val cont = context ?: return super.onCreateDialog(savedInstanceState)
        val adb = AlertDialog.Builder(cont)
        adb.setTitle("Выберите пользователя")
            .setItems(users, DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(
                    getActivity(),
                    "Выбранный кот: " + users[which],
                    Toast.LENGTH_SHORT
                ).show()

            })
        return adb.create()
    }


}