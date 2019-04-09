package ru.nikitaboiko.stoservice

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import ru.nikitaboiko.stoservice.structure.Helpers


class AdminUserList : AppCompatActivity(), AdapterView.OnItemLongClickListener {
    private lateinit var mAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.dataControl.getUserList()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_user_list)

        val listView = findViewById<View>(R.id.activity_admin_user_list_list) as ListView
        val addButton = findViewById<View>(R.id.activity_admin_user_list_add)

        mAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, Helpers.instance.userList
        )
        listView.adapter = mAdapter
        listView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val manager = supportFragmentManager
            val myDialogFragment = UserDeleteDialog()
            val bundle = Bundle()
            bundle.putInt("userId", position)
            myDialogFragment.arguments = bundle
            myDialogFragment.show(manager, "dialog")
        }

    }


    override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
        Toast.makeText(
            baseContext,
            "Введите пароль",
            Toast.LENGTH_SHORT
        ).show()
        val manager = supportFragmentManager
        val myDialogFragment = UserDeleteDialog()
        val bundle = Bundle()
        bundle.putInt("userId", position)
        myDialogFragment.arguments = bundle
        myDialogFragment.show(manager, "dialog")
        return true
    }

    class UserDeleteDialog : DialogFragment() {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val bundle = arguments
            val user = Helpers.instance.userList[bundle?.getInt("userId") ?: 0]
            val title = "Удаление gjkmpjdfntkz"
            val message = "Вы действительно хотите удалить $user?"
            val button1String = "Удалить"
            val button2String = "Отмена"
            val builder = AlertDialog.Builder(context!!)
            builder.setTitle(title)  // заголовок
            builder.setMessage(message) // сообщение
            builder.setPositiveButton(button1String) { dialog, id ->
                App.instance.dataControl.deleteUser(user)
            }
            builder.setNegativeButton(button2String) { dialog, id ->
                dismiss()
            }
            builder.setCancelable(true)
            return builder.create()
        }
    }

    fun updateList() {
        App.instance.dataControl.getUserList()
        mAdapter.notifyDataSetChanged()
    }
}
