package ru.nikitaboiko.stoservice.fragments

import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_user_reg.view.*
import ru.nikitaboiko.stoservice.App
import ru.nikitaboiko.stoservice.R
import ru.nikitaboiko.stoservice.structure.Helpers


class UserRegDialog : DialogFragment() {
    lateinit var login: TextView
    lateinit var password: TextView
    lateinit var passwordRepeat: TextView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val cont = context ?: return super.onCreateDialog(savedInstanceState)
        val activ = activity ?: return super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(cont)
        val inflater = activ.layoutInflater
        val inflatedView = inflater.inflate(R.layout.fragment_user_reg, null)
        builder.setView(inflatedView)
        val buttonAdd = inflatedView.fragment_user_reg_button_add
        login = inflatedView.fragment_user_reg_login
        password = inflatedView.fragment_user_reg_password
        passwordRepeat = inflatedView.fragment_user_reg_password_repeat

        buttonAdd.setOnClickListener {
            addUser()
        }

        return builder.create()
    }

    private fun addUser() {
        if (login.text.isEmpty()) {
            Toast.makeText(
                App.instance().baseContext,
                "Не заполнен логин",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        if (password.text.isEmpty()) {
            Toast.makeText(
                App.instance().baseContext,
                "Не заполнен пароль",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        if (passwordRepeat.text.isEmpty()) {
            Toast.makeText(
                App.instance().baseContext,
                "Повторите пароль",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (password.text.toString().equals(passwordRepeat.text.toString())) {
            val id = App.instance().dataControl.addUser(
                Helpers().delSpaces(login.text.toString(), false),
                Helpers().delSpaces(password.text.toString())
            )
            if (id != null) dismiss()
        } else {
            Toast.makeText(
                App.instance().baseContext,
                "Пароли не совпадают",
                Toast.LENGTH_LONG
            ).show()
        }
    }

}