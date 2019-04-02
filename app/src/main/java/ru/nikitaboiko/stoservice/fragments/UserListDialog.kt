package ru.nikitaboiko.stoservice.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.nikitaboiko.stoservice.App


class UserListDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var users = App.instance().dataControl.getUserList().toTypedArray()
        val cont = context ?: return super.onCreateDialog(savedInstanceState)
        val adb = AlertDialog.Builder(cont)
        adb.setTitle("Выберите пользователя")
        // adb.setItems(users, DialogInterface.OnClickListener())
        /*    setItems(users, DialogInterface.OnClickListener()
        {
            fun onClick(dialog: DialogInterface, which: int) {
                Toast.makeText(getActivity(),
                    "Выбранный кот: ",
                    Toast.LENGTH_SHORT).show();
            })*/
        return adb.create()
    }


}