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
import ru.nikitaboiko.stoservice.R

class UserLoginDialog : DialogFragment() {
    lateinit var login: TextView
    lateinit var password: EditText
    private lateinit var listener: OnFragmentInteractionListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bundle = arguments
        val username = bundle?.getString("user") ?: ""
        val cont = context ?: return super.onCreateDialog(savedInstanceState)
        val activ = activity ?: return super.onCreateDialog(savedInstanceState)
        if (cont is UserLoginDialog.OnFragmentInteractionListener) {
            listener = cont
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
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
            listener?.onFragmentInteraction("workflow", username)
        }

        return builder.create()
    }


    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(currentActivity: String, user: String)
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
    }
}