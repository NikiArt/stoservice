package ru.nikitaboiko.stoservice.fragments

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_user_login.view.*
import ru.nikitaboiko.stoservice.App
import ru.nikitaboiko.stoservice.R
import ru.nikitaboiko.stoservice.structure.Helpers

class UserLoginDialog : DialogFragment() {
    lateinit var login: TextView
    lateinit var password: EditText
    private lateinit var listener: OnFragmentInteractionListener
    var username = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bundle = arguments
        username = bundle?.getString("user") ?: ""
        val cont = context ?: return super.onCreateDialog(savedInstanceState)
        val activ = activity ?: return super.onCreateDialog(savedInstanceState)
        listener = cont as OnFragmentInteractionListener
        val builder = AlertDialog.Builder(cont)
        val inflater = activ.layoutInflater
        val inflatedView = inflater.inflate(R.layout.fragment_user_login, null)
        builder.setView(inflatedView)
        val buttonOk = inflatedView.fragment_user_login_button_ok
        login = inflatedView.fragment_user_login_username
        password = inflatedView.fragment_user_login_password
        login.text = "Пользователь: $username"

        buttonOk.setOnClickListener {
            checkPass(activ)
        }
        password.setOnClickListener {
            checkPass(activ)
        }


        return builder.create()
    }


    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(nextActivity: String, unit: String)
    }

    private fun checkPass(activ: Activity) {
        if (password.editableText.toString().isEmpty()) {
            Toast.makeText(
                activ,
                "Введите пароль",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        if (App.instance().dataControl.passIsCorrect(username, Helpers().delSpaces(password.editableText.toString()))) {
            listener.onFragmentInteraction(if (username.equals("Администратор")) "AdminPanel" else "Workflow", username)
            dismiss()
        }
    }
}