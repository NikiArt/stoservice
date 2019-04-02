package ru.nikitaboiko.stoservice.fragments

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_list_item.view.*
import ru.nikitaboiko.stoservice.App
import ru.nikitaboiko.stoservice.MainActivity
import ru.nikitaboiko.stoservice.R


class UserListAdapter : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    var users: ArrayList<String> = App.instance().dataControl.getUserList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var user: TextView = view.user_list_item_user

        init {
            view.setOnClickListener {
                openUserActivity()
            }
        }

        private fun openUserActivity() {
            val intent = Intent(App.instance().baseContext, MainActivity::class.java)
            startActivity(App.instance().baseContext, intent, null)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.user.text = users[position]
    }
}