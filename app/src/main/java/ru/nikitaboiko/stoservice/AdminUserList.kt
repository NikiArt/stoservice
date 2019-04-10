package ru.nikitaboiko.stoservice

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import ru.nikitaboiko.stoservice.fragments.DeleteListDialog
import ru.nikitaboiko.stoservice.fragments.UserRegDialog
import ru.nikitaboiko.stoservice.structure.Helpers


class AdminUserList : AppCompatActivity(), DeleteListDialog.OnFragmentInteractionListener,
    UserRegDialog.OnFragmentInteractionListener {
    private lateinit var mAdapter: ArrayAdapter<String>
    val manager = supportFragmentManager

    override fun onFragmentInteraction(currentActivity: String, unit: String) {
        when (currentActivity) {
            "updateList" -> {
                updateList()
            }
        }
    }

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
            val myDialogFragment = UserRegDialog()
            val bundle = Bundle()
            bundle.putString("username", Helpers.instance.userList[position])
            myDialogFragment.arguments = bundle
            myDialogFragment.show(manager, "dialog")
        }

        listView.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent, view, position, id ->
            val myDialogFragment = DeleteListDialog()
            val user = Helpers.instance.userList[position]
            val bundle = Bundle()
            bundle.putInt("Id", position)
            bundle.putString("title", "Удаление пользователя")
            bundle.putString("message", "Вы действительно хотите удалить $user?")
            bundle.putString("listType", "userList")
            myDialogFragment.arguments = bundle
            myDialogFragment.show(manager, "dialog")
            true
        }

        addButton.setOnClickListener {
            val myDialogFragment = UserRegDialog()
            myDialogFragment.show(manager, "dialog")
        }

    }

    fun updateList() {
        App.instance.dataControl.getUserList()
        mAdapter.notifyDataSetChanged()
    }
}
