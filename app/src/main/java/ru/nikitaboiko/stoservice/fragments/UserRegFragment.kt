package ru.nikitaboiko.stoservice.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_user_reg.view.*
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import ru.nikitaboiko.stoservice.App
import ru.nikitaboiko.stoservice.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UserRegFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    lateinit var login: TextView
    lateinit var password: TextView
    lateinit var passwordRepeat: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflatedView = inflater.inflate(R.layout.fragment_user_reg, container, false)
        val buttonAdd = inflatedView.fragment_user_reg_button_add
        login = inflatedView.fragment_user_reg_login
        password = inflatedView.fragment_user_reg_password
        passwordRepeat = inflatedView.fragment_user_reg_password_repeat

        buttonAdd.setOnClickListener {
            addUser()
        }

        return inflatedView
    }

    private fun addUser() {
        if (login.text.isEmpty()) {
            Toast.makeText(
                App.instance()?.baseContext,
                "Не заполнен логин",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        if (password.text.isEmpty()) {
            Toast.makeText(
                App.instance()?.baseContext,
                "Не заполнен пароль",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        if (passwordRepeat.text.isEmpty()) {
            Toast.makeText(
                App.instance()?.baseContext,
                "Повторите пароль",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (password.text.toString().equals(passwordRepeat.text.toString())) {
            val passMd5 = Hex.encodeHex(DigestUtils.md5("vicomlite" + password.text.toString())).joinToString("")
            App.instance()?.dataControl?.addUser(login.text.toString(), passMd5)
        } else {
            Toast.makeText(
                App.instance()?.baseContext,
                "Пароли не совпадают ${password.text.toString()} and ${passwordRepeat.text.toString()}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserRegFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
